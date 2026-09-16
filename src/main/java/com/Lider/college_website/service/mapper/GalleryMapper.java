package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.AcademicYearResponse;
import com.Lider.college_website.dto.response.GalleryImageResponse;
import com.Lider.college_website.dto.response.GalleryResponse;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.entity.Gallery;
import com.Lider.college_website.entity.GalleryImage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class GalleryMapper {

    private GalleryMapper() {}

    public static AcademicYearResponse toResponse(AcademicYear year) {
        return AcademicYearResponse.builder()
                .id(year.getId())
                .yearLabel(year.getYearLabel())
                .startDate(year.getStartDate())
                .endDate(year.getEndDate())
                .active(year.isActive())
                .build();
    }

    public static GalleryImageResponse toResponse(GalleryImage image) {
        return GalleryImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .caption(image.getCaption())
                .createdAt(image.getCreatedAt())
                .build();
    }

    /** Summary form — no images loaded, just the count. Used for list views. */
    public static GalleryResponse toSummaryResponse(Gallery gallery, long imageCount) {
        return baseBuilder(gallery)
                .images(Collections.emptyList())
                .imageCount(imageCount)
                .build();
    }

    /** Full form — includes all images. Used for single-gallery detail view. */
    public static GalleryResponse toDetailResponse(Gallery gallery, List<GalleryImage> images) {
        List<GalleryImageResponse> imageResponses = images.stream()
                .map(GalleryMapper::toResponse)
                .collect(Collectors.toList());

        return baseBuilder(gallery)
                .images(imageResponses)
                .imageCount(imageResponses.size())
                .build();
    }

    private static GalleryResponse.GalleryResponseBuilder baseBuilder(Gallery gallery) {
        return GalleryResponse.builder()
                .id(gallery.getId())
                .title(gallery.getTitle())
                .description(gallery.getDescription())
                .academicYearId(gallery.getAcademicYear().getId())
                .academicYearLabel(gallery.getAcademicYear().getYearLabel())
                .createdAt(gallery.getCreatedAt());
    }
}