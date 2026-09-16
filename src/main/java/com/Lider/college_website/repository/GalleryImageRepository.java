package com.Lider.college_website.repository;

import com.Lider.college_website.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findAllByGalleryId(Long galleryId);
    long countByGalleryId(Long galleryId);
}