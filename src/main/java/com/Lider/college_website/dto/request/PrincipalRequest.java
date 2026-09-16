
package com.Lider.college_website.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrincipalRequest {

    @NotBlank(message = "From date is required")
    private String fromDate;   // "27-10-2022" format, or use LocalDate if strict

    private String toDate;     // "09-03-2023" or "Till Date"

    @NotBlank(message = "Principal name is required")
    private String name;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Designation is required")
    private String designation;

    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;
}