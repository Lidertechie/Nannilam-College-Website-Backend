package com.Lider.college_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaanMudhalvanSchemeRequestDTO {

    private Long staffId;    // required - auto-fetches name, qualification & department
    private String position; // required, e.g. "SPOC"
}