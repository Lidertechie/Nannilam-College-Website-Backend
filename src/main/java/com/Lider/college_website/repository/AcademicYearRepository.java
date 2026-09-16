package com.Lider.college_website.repository;

import com.Lider.college_website.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    boolean existsByYearLabel(String yearLabel);
    Optional<AcademicYear> findByActiveTrue();
    List<AcademicYear> findAllByOrderByYearLabelDesc();
}