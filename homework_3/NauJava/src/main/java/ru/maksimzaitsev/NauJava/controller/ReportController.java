package ru.maksimzaitsev.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maksimzaitsev.NauJava.dataAccess.UserRepository;
import ru.maksimzaitsev.NauJava.dbEntities.EncryptedPassword;
import ru.maksimzaitsev.NauJava.repositories.EncryptedPasswordRepository;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptedPasswordRepository encryptedPasswordRepository;

    @GetMapping("/report")
    public String generateReport(Model model) {
        long startTotalTime = System.currentTimeMillis();
        long[] userCount = new long[1];
        long[] userTime = new long[1];
        long[] passwordTime = new long[1];
        List<EncryptedPassword>[] encryptedPasswords = new List[1];

        Thread userCountThread = new Thread(() -> {
            long userStartTime = System.currentTimeMillis();
            long count = userRepository.count();
            userCount[0] = count;
            userTime[0] = System.currentTimeMillis() - userStartTime;
        });

        Thread passwordsThread = new Thread(() -> {
            long passwordsStartTime = System.currentTimeMillis();
            List<EncryptedPassword> passwords = encryptedPasswordRepository.findAll();
            encryptedPasswords[0] = passwords;
            passwordTime[0] = System.currentTimeMillis() - passwordsStartTime;
        });

        userCountThread.start();
        passwordsThread.start();

        try {
            userCountThread.join();
            passwordsThread.join();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }

        long totalTime = System.currentTimeMillis() - startTotalTime;

        model.addAttribute("userCount", userCount[0]);
        model.addAttribute("userCalculationTime", userTime[0]);
        model.addAttribute("passwordCalculationTime", passwordTime[0]);
        model.addAttribute("totalTime", totalTime);
        model.addAttribute("encryptedPasswords", encryptedPasswords[0]);

        return "report";
    }
}
