package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private String description;
    private Long academicYearId;
    private String imageUrl;
    private String academicYearLabel;
    private boolean active;
    private LocalDateTime createdAt;
}