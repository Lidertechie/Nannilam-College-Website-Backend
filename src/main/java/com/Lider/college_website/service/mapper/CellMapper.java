package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.CellResponse;
import com.Lider.college_website.entity.Cell;

public final class CellMapper {

    private CellMapper() {
    }

    public static CellResponse toResponse(Cell cell) {

        String departmentName = null;

        if (cell.getCourse() != null
                && cell.getCourse().getDepartmentDivision() != null
                && cell.getCourse().getDepartmentDivision().getDepartment() != null) {

            departmentName = cell.getCourse()
                    .getDepartmentDivision()
                    .getDepartment()
                    .getName();
        }

        return CellResponse.builder()
                .id(cell.getId())
                .category(cell.getCategory())

                .courseId(
                        cell.getCourse() != null
                                ? cell.getCourse().getId()
                                : null
                )

                .courseName(
                        cell.getCourse() != null
                                ? cell.getCourse().getCourseName()
                                : null
                )

                .departmentName(departmentName)

                .active(cell.isActive())
                .createdAt(cell.getCreatedAt())

                .staffId(cell.getStaff().getId())
                .staffName(cell.getStaff().getName())
                .staffDesignation(cell.getStaff().getDesignation())

                .build();
    }
}