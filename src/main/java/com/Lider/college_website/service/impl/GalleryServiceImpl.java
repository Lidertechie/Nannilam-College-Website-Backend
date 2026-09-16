package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.GalleryImageRequest;
import com.Lider.college_website.dto.request.GalleryRequest;
import com.Lider.college_website.dto.response.GalleryResponse;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.entity.Gallery;
import com.Lider.college_website.entity.GalleryImage;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.AcademicYearRepository;
import com.Lider.college_website.repository.GalleryImageRepository;
import com.Lider.college_website.repository.GalleryRepository;
import com.Lider.college_website.service.GalleryService;
import com.Lider.college_website.service.mapper.GalleryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public GalleryResponse createGallery(GalleryRequest request) {
        AcademicYear year = academicYearRepository.findById(request.getAcademicYearId())
                .orElseThrow(() -> ResourceNotFoundException.forEntity(
                        "AcademicYear", "id", request.getAcademicYearId()));

        Gallery gallery = Gallery.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .academicYear(year)
                .build();

        Gallery saved = galleryRepository.save(gallery);
        return GalleryMapper.toSummaryResponse(saved, 0);
    }

    @Override
    @Transactional
    public GalleryResponse updateGallery(Long id, GalleryRequest request) {
        Gallery gallery = getGalleryOrThrow(id);

        if (!gallery.getAcademicYear().getId().equals(request.getAcademicYearId())) {
            AcademicYear year = academicYearRepository.findById(request.getAcademicYearId())
                    .orElseThrow(() -> ResourceNotFoundException.forEntity(
                            "AcademicYear", "id", request.getAcademicYearId()));
            gallery.setAcademicYear(year);
        }

        gallery.setTitle(request.getTitle());
        gallery.setDescription(request.getDescription());

        long count = galleryImageRepository.countByGalleryId(id);
        return GalleryMapper.toSummaryResponse(gallery, count);
    }

    @Override
    @Transactional
    public void deleteGallery(Long id) {
        Gallery gallery = getGalleryOrThrow(id);
        galleryRepository.delete(gallery); // images cascade-deleted via DB FK or explicit cleanup below
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryResponse getGalleryById(Long id) {
        Gallery gallery = getGalleryOrThrow(id);
        List<GalleryImage> images = galleryImageRepository.findAllByGalleryId(id);
        return GalleryMapper.toDetailResponse(gallery, images);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryResponse> getGalleriesByYear(Long academicYearId) {
        return galleryRepository.findAllByAcademicYearIdOrderByCreatedAtDesc(academicYearId).stream()
                .map(g -> {
                    List<GalleryImage> images =
                            galleryImageRepository.findAllByGalleryId(g.getId());

                    return GalleryMapper.toDetailResponse(g, images);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryResponse> getAllGalleries() {
        return galleryRepository.findAll().stream()
                .map(g -> {
                    List<GalleryImage> images =
                            galleryImageRepository.findAllByGalleryId(g.getId());

                    return GalleryMapper.toDetailResponse(g, images);
                })
                .toList();
    }

    @Override
    @Transactional
    public GalleryResponse addImage(Long galleryId, GalleryImageRequest request) {
        Gallery gallery = getGalleryOrThrow(galleryId);

        GalleryImage image = GalleryImage.builder()
                .gallery(gallery)
                .imageUrl(request.getImageUrl())
                .caption(request.getCaption())
                .build();

        galleryImageRepository.save(image);

        List<GalleryImage> allImages = galleryImageRepository.findAllByGalleryId(galleryId);
        return GalleryMapper.toDetailResponse(gallery, allImages);
    }

    @Override
    @Transactional
    public void deleteImage(Long galleryId, Long imageId) {
        GalleryImage image = galleryImageRepository.findById(imageId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("GalleryImage", "id", imageId));

        if (!image.getGallery().getId().equals(galleryId)) {
            throw new ResourceNotFoundException(
                    "Image " + imageId + " does not belong to gallery " + galleryId);
        }

        galleryImageRepository.delete(image);
    }

    private Gallery getGalleryOrThrow(Long id) {
        return galleryRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Gallery", "id", id));
    }
}