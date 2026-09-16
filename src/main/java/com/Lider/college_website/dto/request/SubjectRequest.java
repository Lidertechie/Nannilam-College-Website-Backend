package com.Lider.college_website.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    @Size(max = 150, message = "Subject name must not exceed 150 characters")
    private String subjectName;

    @NotNull(message = "Course id is required")
    private Long courseId;
}