package com.Lider.college_website.dto.request;

import com.Lider.college_website.enums.CellCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CellRequest {

    @NotNull(message = "Category is required")
    private CellCategory category;

    private Long courseId;

    @NotNull(message = "Staff id is required")
    private Long staffId;
}