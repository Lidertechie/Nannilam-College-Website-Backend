package com.Lider.college_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaanMudhalvanSchemeResponseDTO {

    private Long id;
    private Long staffId;
    private String coordinatorName; // auto
    private String qualification;   // auto
    private Long departmentId;      // auto
    private String departmentName;  // auto
    private String position;        // manual
    private Boolean verified;       // auto
    private String academicYear;    // auto
}