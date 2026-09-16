package com.Lider.college_website.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScholarshipOfficerGroupResponse {
    private Long id;
    private String name;
    private boolean active;
}