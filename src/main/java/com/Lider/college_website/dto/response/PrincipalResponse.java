package com.Lider.college_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalResponse {
    private Long id;
    private String fromDate;
    private String toDate;
    private String name;
    private String qualification;
    private String designation;
    private String imageUrl;
}