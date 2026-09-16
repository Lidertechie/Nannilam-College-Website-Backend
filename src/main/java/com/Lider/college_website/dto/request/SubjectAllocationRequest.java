package com.Lider.college_website.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubjectAllocationRequest {

    @NotNull(message = "Subject id is required")
    private Long subjectId;

    @NotNull(message = "Staff id is required")
    private Long staffId;
}