package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CircularResponse {
    private Long id;
    private String circularTitle;
    private LocalDate date;
    private String description;
    private Long academicYearId;
    private String academicYearLabel;
    private String documentUrl;
    private boolean active;
    private LocalDateTime createdAt;
}