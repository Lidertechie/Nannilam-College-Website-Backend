package com.Lider.college_website.repository;

import com.Lider.college_website.entity.AcademicCalendarFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicCalendarFileRepository extends JpaRepository<AcademicCalendarFile, Long> {
    List<AcademicCalendarFile> findAllByAcademicCalendarId(Long academicCalendarId);
    long countByAcademicCalendarId(Long academicCalendarId);
}