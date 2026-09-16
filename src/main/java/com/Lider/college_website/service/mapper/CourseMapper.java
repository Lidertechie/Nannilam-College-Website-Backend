package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.CourseResponse;
import com.Lider.college_website.entity.Course;

public final class CourseMapper {

    private CourseMapper() {}

    public static CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .category(course.getCategory())
                .courseName(course.getCourseName())
                .mediumOfInstruction(course.getMediumOfInstruction())
                .active(course.isActive())
                .createdAt(course.getCreatedAt())
                .departmentDivisionId(course.getDepartmentDivision().getId())
                .departmentId(course.getDepartmentDivision().getDepartment().getId())
                .departmentName(course.getDepartmentDivision().getDepartment().getName())
                .build();
    }
}