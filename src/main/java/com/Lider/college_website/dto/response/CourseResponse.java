package com.Lider.college_website.dto.response;

import com.Lider.college_website.enums.CourseCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseResponse {
    private Long id;
    private CourseCategory category;
    private String courseName;
    private String mediumOfInstruction;
    private boolean active;
    private LocalDateTime createdAt;
    private Long departmentDivisionId;
    private Long departmentId;
    private String departmentName;
}