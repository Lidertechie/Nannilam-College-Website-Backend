package com.Lider.college_website.dto.response;

import com.Lider.college_website.enums.CourseCategory;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentDivisionResponse {
    private Long id;
    private CourseCategory category;
    private boolean active;
    private Long departmentId;
    private String departmentName;
}