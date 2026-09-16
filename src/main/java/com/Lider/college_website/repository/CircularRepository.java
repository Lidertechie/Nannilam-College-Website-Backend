package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Circular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CircularRepository extends JpaRepository<Circular, Long> {

    List<Circular> findAllByActiveTrueOrderByDateDesc();

    List<Circular> findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(Long academicYearId);

    List<Circular> findAllByOrderByDateDesc();
}