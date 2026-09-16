package com.Lider.college_website.repository;

import com.Lider.college_website.entity.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Long> {

    List<AcademicCalendar> findAllByActiveTrueOrderByCreatedAtDesc();

    List<AcademicCalendar> findAllByAcademicYearIdAndActiveTrueOrderByCreatedAtDesc(Long academicYearId);

    List<AcademicCalendar> findAllByOrderByCreatedAtDesc(); // admin, includes inactive
}