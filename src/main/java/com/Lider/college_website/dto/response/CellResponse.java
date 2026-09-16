package com.Lider.college_website.dto.response;

import com.Lider.college_website.enums.CellCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CellResponse {
    private Long id;
    private CellCategory category;
    private Long courseId;
    private String courseName;
    private String departmentName;
    private boolean active;
    private LocalDateTime createdAt;

    // Flattened staff info — avoids nesting a full StaffResponse for a simple reference
    private Long staffId;
    private String staffName;
    private String staffDesignation;
}