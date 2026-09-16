package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GalleryResponse {
    private Long id;
    private String title;
    private String description;
    private String academicYearLabel;
    private Long academicYearId;
    private LocalDateTime createdAt;
    private List<GalleryImageResponse> images;
    private long imageCount;
}