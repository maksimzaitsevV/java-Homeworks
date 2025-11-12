package ru.maksimzaitsev.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.maksimzaitsev.NauJava.entity.Report;
import ru.maksimzaitsev.NauJava.entity.ReportStatus;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);
    List<Report> findAll();
}