package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AcademicYearResponse {
    private Long id;
    private String yearLabel;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}