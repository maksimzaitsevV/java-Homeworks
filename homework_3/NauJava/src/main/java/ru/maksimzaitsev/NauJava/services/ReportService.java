package ru.maksimzaitsev.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maksimzaitsev.NauJava.dataAccess.UserRepository;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.entity.Report;
import ru.maksimzaitsev.NauJava.entity.ReportStatus;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;
import ru.maksimzaitsev.NauJava.repositories.ReportRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptedPasswordRepository encryptedPasswordRepository;

    public String getReportContent(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Отчет не найден"));
        return report.getContent();
    }

    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED, "Отчет создается");
        report = reportRepository.save(report);
        return report.getId();
    }

    public void generateReportAsync(Long reportId) {
        CompletableFuture.supplyAsync(() -> {
            long startTotalTime = System.currentTimeMillis();

            long[] userCount = new long[1];
            long[] userCalculationTime = new long[1];
            long[] passwordCalculationTime = new long[1];
            List<EncryptedPassword>[] encryptedPasswords = new List[1];

            Thread userCountThread = new Thread(() -> {
                long userStartTime = System.currentTimeMillis();
                long count = userRepository.count();
                userCount[0] = count;
                userCalculationTime[0] = System.currentTimeMillis() - userStartTime;
            });

            Thread passwordsThread = new Thread(() -> {
                long passwordsStartTime = System.currentTimeMillis();
                List<EncryptedPassword> passwords = encryptedPasswordRepository.findAll();
                encryptedPasswords[0] = passwords;
                passwordCalculationTime[0] = System.currentTimeMillis() - passwordsStartTime;
            });

            userCountThread.start();
            passwordsThread.start();

            try {
                userCountThread.join();
                passwordsThread.join();

                long totalTime = System.currentTimeMillis() - startTotalTime;

                String reportContent = String.format(
                        """
                                Количество пользователей: %d
                                Время поиска пользователей: %d мс
                                Время создания таблицы: %d мс
                                Общее время: %d мс
                                Количество паролей: %d""",
                        userCount[0], userCalculationTime[0], passwordCalculationTime[0],
                        totalTime, encryptedPasswords[0].size()
                );

                Report report = reportRepository.findById(reportId)
                        .orElseThrow(() -> new RuntimeException("Отчет не найден"));
                report.setStatus(ReportStatus.COMPLETED);
                report.setContent(reportContent);
                reportRepository.save(report);

                return "Отчет создан";

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

        }).thenAccept(System.out::println).exceptionally(ex -> {
            System.out.println(ex.getMessage());

            try {
                Report report = reportRepository.findById(reportId)
                        .orElseThrow(() -> new RuntimeException("Отчет не найден"));
                report.setStatus(ReportStatus.ERROR);
                report.setContent(ex.getMessage());
                reportRepository.save(report);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        });
    }
}