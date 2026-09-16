package com.Lider.college_website.dto.request;

import com.Lider.college_website.enums.CourseCategory;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddDivisionRequest {

    @NotNull(message = "Category is required")
    private CourseCategory category;
}