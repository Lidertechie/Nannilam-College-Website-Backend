package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.*;
import com.Lider.college_website.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public final class AcademicStructureMapper {

    private AcademicStructureMapper() {}

    public static DepartmentDivisionResponse toResponse(DepartmentDivision division) {
        return DepartmentDivisionResponse.builder()
                .id(division.getId())
                .category(division.getCategory())
                .active(division.isActive())
                .departmentId(division.getDepartment().getId())
                .departmentName(division.getDepartment().getName())
                .build();
    }

    public static DepartmentResponse toResponse(Department department, List<DepartmentDivision> divisions) {
        List<DepartmentDivisionResponse> divisionResponses = divisions.stream()
                .map(AcademicStructureMapper::toResponse)
                .collect(Collectors.toList());

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .active(department.isActive())
                .createdAt(department.getCreatedAt())
                .divisions(divisionResponses)
                .build();
    }

    public static CourseResponse toResponse(Course course) {
        DepartmentDivision division = course.getDepartmentDivision();
        return CourseResponse.builder()
                .id(course.getId())
                .category(course.getCategory())
                .courseName(course.getCourseName())
                .mediumOfInstruction(course.getMediumOfInstruction())
                .active(course.isActive())
                .createdAt(course.getCreatedAt())
                .departmentDivisionId(division.getId())
                .departmentId(division.getDepartment().getId())
                .departmentName(division.getDepartment().getName())
                .build();
    }

    public static SubjectAllocationResponse toResponse(SubjectAllocation allocation) {
        return SubjectAllocationResponse.builder()
                .id(allocation.getId())
                .active(allocation.isActive())
                .subjectId(allocation.getSubject().getId())
                .subjectName(allocation.getSubject().getSubjectName())
                .staffId(allocation.getStaff().getId())
                .staffName(allocation.getStaff().getName())
                .staffDesignation(allocation.getStaff().getDesignation())
                .staffQualification(allocation.getStaff().getQualification())
                .staffType(allocation.getStaff().getStaffType().getDisplayName())
                .staffImageUrl(allocation.getStaff().getImageUrl())
                .documentUrl(allocation.getStaff().getDocumentUrl())
                .build();
    }

    public static SubjectResponse toResponse(Subject subject, List<SubjectAllocation> allocations) {
        List<SubjectAllocationResponse> allocationResponses = allocations.stream()
                .map(AcademicStructureMapper::toResponse)
                .collect(Collectors.toList());

        return SubjectResponse.builder()
                .id(subject.getId())
                .subjectName(subject.getSubjectName())
                .active(subject.isActive())
                .createdAt(subject.getCreatedAt())
                .courseId(subject.getCourse().getId())
                .courseName(subject.getCourse().getCourseName())
                .allocations(allocationResponses)
                .build();
    }

    public static SubjectResponse toResponseWithoutAllocations(Subject subject) {
        return SubjectResponse.builder()
                .id(subject.getId())
                .subjectName(subject.getSubjectName())
                .active(subject.isActive())
                .createdAt(subject.getCreatedAt())
                .courseId(subject.getCourse().getId())
                .courseName(subject.getCourse().getCourseName())
                .build();
    }
}