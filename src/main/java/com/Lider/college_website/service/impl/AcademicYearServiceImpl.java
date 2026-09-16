package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.AcademicYearRequest;
import com.Lider.college_website.dto.response.AcademicYearResponse;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.AcademicYearRepository;
import com.Lider.college_website.service.AcademicYearService;
import com.Lider.college_website.service.mapper.GalleryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public AcademicYearResponse create(AcademicYearRequest request) {

        if (academicYearRepository.existsByYearLabel(request.getYearLabel())) {
            throw DuplicateResourceException.forField(
                    "AcademicYear", "yearLabel", request.getYearLabel());
        }

        boolean firstAcademicYear = academicYearRepository.count() == 0;

        AcademicYear year = AcademicYear.builder()
                .yearLabel(request.getYearLabel())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(firstAcademicYear)
                .build();

        return GalleryMapper.toResponse(academicYearRepository.save(year));
    }

    @Override
    @Transactional
    public AcademicYearResponse update(Long id, AcademicYearRequest request) {
        AcademicYear year = getOrThrow(id);
        year.setYearLabel(request.getYearLabel());
        year.setStartDate(request.getStartDate());
        year.setEndDate(request.getEndDate());
        return GalleryMapper.toResponse(year);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        AcademicYear year = getOrThrow(id);

        if (year.isActive()) {
            throw new IllegalStateException(
                    "Cannot delete the active academic year. Activate another academic year first.");
        }

        academicYearRepository.delete(year);
    }

//    @Override
//    @Transactional
//    public AcademicYearResponse setActive(Long id) {
//        // Deactivate whichever year is currently active, then activate the requested one.
//        academicYearRepository.findByActiveTrue()
//                .ifPresent(current -> current.setActive(false));
//
//        AcademicYear year = getOrThrow(id);
//        year.setActive(true);
//        return GalleryMapper.toResponse(year);
//    }

    @Override
    @Transactional
    public AcademicYearResponse setActive(Long id) {

        AcademicYear year = getOrThrow(id);

        if (year.isActive()) {
            return GalleryMapper.toResponse(year);
        }

        academicYearRepository.findByActiveTrue()
                .ifPresent(current -> current.setActive(false));

        year.setActive(true);

        return GalleryMapper.toResponse(year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYearResponse> getAll() {
        return academicYearRepository.findAllByOrderByYearLabelDesc().stream()
                .map(GalleryMapper::toResponse)
                .collect(Collectors.toList());
    }

    private AcademicYear getOrThrow(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicYear", "id", id));
    }
}