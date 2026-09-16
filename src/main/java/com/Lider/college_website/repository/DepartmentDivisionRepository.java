package com.Lider.college_website.repository;

import com.Lider.college_website.entity.DepartmentDivision;
import com.Lider.college_website.enums.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentDivisionRepository extends JpaRepository<DepartmentDivision, Long> {
    List<DepartmentDivision> findAllByDepartmentId(Long departmentId);
    Optional<DepartmentDivision> findByDepartmentIdAndCategory(Long departmentId, CourseCategory category);
    boolean existsByDepartmentIdAndCategory(Long departmentId, CourseCategory category);
}