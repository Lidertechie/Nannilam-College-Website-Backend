package com.Lider.college_website.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GalleryImageResponse {
    private Long id;
    private String imageUrl;
    private String caption;
    private LocalDateTime createdAt;
}