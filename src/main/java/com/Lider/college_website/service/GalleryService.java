package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.GalleryImageRequest;
import com.Lider.college_website.dto.request.GalleryRequest;
import com.Lider.college_website.dto.response.GalleryResponse;

import java.util.List;

public interface GalleryService {

    GalleryResponse createGallery(GalleryRequest request);
    GalleryResponse updateGallery(Long id, GalleryRequest request);
    void deleteGallery(Long id);
    GalleryResponse getGalleryById(Long id);              // full detail, with images
    List<GalleryResponse> getGalleriesByYear(Long academicYearId); // summary list
    List<GalleryResponse> getAllGalleries();
    GalleryResponse addImage(Long galleryId, GalleryImageRequest request);
    void deleteImage(Long galleryId, Long imageId);
}