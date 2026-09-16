package com.Lider.college_website.repository;

import com.Lider.college_website.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findAllByAcademicYearId(Long academicYearId);
    List<Gallery> findAllByAcademicYearIdOrderByCreatedAtDesc(Long academicYearId);
}