package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AcademicCalendarResponse {
    private Long id;
    private String title;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;

    private Long academicYearId;
    private String academicYearLabel;

    private List<AcademicCalendarFileResponse> files;
    private long fileCount;
}