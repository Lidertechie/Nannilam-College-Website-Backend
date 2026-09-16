package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.CircularResponse;
import com.Lider.college_website.entity.Circular;

public final class CircularMapper {

    private CircularMapper() {}

    public static CircularResponse toResponse(Circular circular) {
        return CircularResponse.builder()
                .id(circular.getId())
                .circularTitle(circular.getCircularTitle())
                .date(circular.getDate())
                .description(circular.getDescription())
                .academicYearId(circular.getAcademicYear().getId())
                .academicYearLabel(circular.getAcademicYear().getYearLabel())
                .documentUrl(circular.getDocumentUrl())
                .active(circular.isActive())
                .createdAt(circular.getCreatedAt())
                .build();
    }
}