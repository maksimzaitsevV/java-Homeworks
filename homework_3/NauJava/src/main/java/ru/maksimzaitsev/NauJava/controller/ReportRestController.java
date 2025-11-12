package ru.maksimzaitsev.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maksimzaitsev.NauJava.entity.Report;
import ru.maksimzaitsev.NauJava.entity.ReportStatus;
import ru.maksimzaitsev.NauJava.repositories.ReportRepository;
import ru.maksimzaitsev.NauJava.services.ReportService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> createReport() {
        long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        Map<String, Object> response = new HashMap<>();
        response.put("reportId", reportId);
        response.put("status", "CREATED");
        response.put("message", "Отчет создан");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Map<String, Object>> getReportContent(@PathVariable Long reportId) {
        try {
            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Отчет не найден"));

            Map<String, Object> response = new HashMap<>();
            response.put("reportId", reportId);
            response.put("status", report.getStatus().toString());

            if (report.getStatus() == ReportStatus.CREATED) {
                response.put("message", "Отчет создается");
                response.put("content", report.getContent());
            } else if (report.getStatus() == ReportStatus.ERROR) {
                response.put("message", "Ошибка при создании отчета");
                response.put("content", report.getContent());
            } else if (report.getStatus() == ReportStatus.COMPLETED) {
                response.put("message", "Отчет успешно создан ");
                response.put("content", report.getContent());
            }

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Отчет не найден");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}