package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Course;
import com.Lider.college_website.enums.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByActiveTrueOrderByCourseNameAsc();

    List<Course> findAllByCategoryAndActiveTrueOrderByCourseNameAsc(CourseCategory category);

    List<Course> findAllByOrderByCourseNameAsc(); // admin view, includes inactive

    List<Course> findAllByDepartmentDivisionIdAndActiveTrue(Long departmentDivisionId);

    List<Course> findAllByDepartmentDivisionId(Long departmentDivisionId); //

    boolean existsByDepartmentDivisionIdAndCourseNameIgnoreCase(Long departmentDivisionId, String courseName);

    boolean existsByDepartmentDivisionIdAndCourseNameIgnoreCaseAndIdNot(
            Long departmentDivisionId, String courseName, Long id);

}