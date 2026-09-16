package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AcademicCalendarFileResponse {
    private Long id;
    private String fileUrl;
    private String fileName;
    private LocalDateTime createdAt;
}