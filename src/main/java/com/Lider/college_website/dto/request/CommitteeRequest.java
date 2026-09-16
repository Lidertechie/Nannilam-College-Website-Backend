package com.Lider.college_website.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitteeRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "File URL is required")
    private String fileUrl;
}