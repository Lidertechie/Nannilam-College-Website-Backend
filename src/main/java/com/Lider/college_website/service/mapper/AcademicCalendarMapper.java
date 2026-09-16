package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.AcademicCalendarFileResponse;
import com.Lider.college_website.dto.response.AcademicCalendarResponse;
import com.Lider.college_website.entity.AcademicCalendar;
import com.Lider.college_website.entity.AcademicCalendarFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class AcademicCalendarMapper {

    private AcademicCalendarMapper() {}

    public static AcademicCalendarFileResponse toResponse(AcademicCalendarFile file) {
        return AcademicCalendarFileResponse.builder()
                .id(file.getId())
                .fileUrl(file.getFileUrl())
                .fileName(file.getFileName())
                .createdAt(file.getCreatedAt())
                .build();
    }

    /** Summary form — no files loaded, just the count. Used for list views. */
    public static AcademicCalendarResponse toSummaryResponse(AcademicCalendar calendar, long fileCount) {
        return baseBuilder(calendar)
                .files(Collections.emptyList())
                .fileCount(fileCount)
                .build();
    }

    /** Full form — includes all files. Used for single-topic detail view. */
    public static AcademicCalendarResponse toDetailResponse(
            AcademicCalendar calendar, List<AcademicCalendarFile> files) {

        List<AcademicCalendarFileResponse> fileResponses = files.stream()
                .map(AcademicCalendarMapper::toResponse)
                .collect(Collectors.toList());

        return baseBuilder(calendar)
                .files(fileResponses)
                .fileCount(fileResponses.size())
                .build();
    }

    private static AcademicCalendarResponse.AcademicCalendarResponseBuilder baseBuilder(AcademicCalendar calendar) {
        return AcademicCalendarResponse.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .description(calendar.getDescription())
                .active(calendar.isActive())
                .createdAt(calendar.getCreatedAt())
                .academicYearId(calendar.getAcademicYear().getId())
                .academicYearLabel(calendar.getAcademicYear().getYearLabel());
    }
}