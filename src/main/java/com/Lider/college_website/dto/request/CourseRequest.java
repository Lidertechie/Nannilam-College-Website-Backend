package com.Lider.college_website.dto.request;

import com.Lider.college_website.enums.CourseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseRequest {

    @NotNull(message = "Category is required")
    private CourseCategory category;

    @NotBlank(message = "Course name is required")
    @Size(max = 150, message = "Course name must not exceed 150 characters")
    private String courseName;

    @NotBlank(message = "Medium of instruction is required")
    @Size(max = 50, message = "Medium of instruction must not exceed 50 characters")
    private String mediumOfInstruction;

    @NotNull(message = "Department division id is required")
    private Long departmentDivisionId;
}