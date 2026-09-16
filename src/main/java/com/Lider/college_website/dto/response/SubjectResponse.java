package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectResponse {
    private Long id;
    private String subjectName;
    private boolean active;
    private LocalDateTime createdAt;

    private Long courseId;
    private String courseName;

    private List<SubjectAllocationResponse> allocations; // who teaches this subject
}