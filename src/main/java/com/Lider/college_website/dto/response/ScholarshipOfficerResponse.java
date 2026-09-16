package com.Lider.college_website.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScholarshipOfficerResponse {
    private Long id;
    private String role;
    private boolean active;

    private Long groupId;
    private String groupName;

    private Long staffId;
    private String staffName;
    private String staffDesignation;
}