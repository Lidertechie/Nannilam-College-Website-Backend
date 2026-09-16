package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.GalleryImageRequest;
import com.Lider.college_website.dto.request.GalleryRequest;
import com.Lider.college_website.dto.response.GalleryResponse;
import com.Lider.college_website.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galleries")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<GalleryResponse>> getGalleries(
            @RequestParam(required = false) Long academicYearId) {

        if (academicYearId == null) {
            return ResponseEntity.ok(galleryService.getAllGalleries());
        }

        return ResponseEntity.ok(galleryService.getGalleriesByYear(academicYearId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(galleryService.getGalleryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<GalleryResponse> create(@Valid @RequestBody GalleryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.createGallery(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryResponse> update(
            @PathVariable Long id, @Valid @RequestBody GalleryRequest request) {
        return ResponseEntity.ok(galleryService.updateGallery(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<GalleryResponse> addImage(
            @PathVariable Long id, @Valid @RequestBody GalleryImageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.addImage(id, request));
    }

    @DeleteMapping("/{galleryId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long galleryId, @PathVariable Long imageId) {
        galleryService.deleteImage(galleryId, imageId);
        return ResponseEntity.noContent().build();
    }
}