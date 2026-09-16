package com.Lider.college_website.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectAllocationResponse {

    private Long id;
    private boolean active;

    private Long subjectId;
    private String subjectName;

    private Long staffId;
    private String staffName;
    private String staffDesignation;
    private String staffQualification;
    private String staffType;
    private String staffImageUrl;
    private String documentUrl;
}