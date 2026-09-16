package com.Lider.college_website.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CircularRequest {

    @NotBlank(message = "Circular title is required")
    @Size(max = 200, message = "Circular title must not exceed 200 characters")
    private String circularTitle;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Academic year is required")
    private Long academicYearId;

    @Size(max = 500, message = "Document URL must not exceed 500 characters")
    private String documentUrl;
}