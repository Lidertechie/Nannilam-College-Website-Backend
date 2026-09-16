package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.CircularRequest;
import com.Lider.college_website.dto.response.CircularResponse;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.entity.Circular;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.AcademicYearRepository;
import com.Lider.college_website.repository.CircularRepository;
import com.Lider.college_website.service.CircularService;
import com.Lider.college_website.service.mapper.CircularMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CircularServiceImpl implements CircularService {

    private final CircularRepository circularRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public CircularResponse create(CircularRequest request) {
        AcademicYear year = getYearOrThrow(request.getAcademicYearId());

        Circular circular = Circular.builder()
                .circularTitle(request.getCircularTitle())
                .date(request.getDate())
                .description(request.getDescription())
                .academicYear(year)
                .documentUrl(request.getDocumentUrl())
                .build();

        return CircularMapper.toResponse(circularRepository.save(circular));
    }

    @Override
    @Transactional
    public CircularResponse update(Long id, CircularRequest request) {
        Circular circular = getOrThrow(id);

        if (!circular.getAcademicYear().getId().equals(request.getAcademicYearId())) {
            circular.setAcademicYear(getYearOrThrow(request.getAcademicYearId()));
        }

        circular.setCircularTitle(request.getCircularTitle());
        circular.setDate(request.getDate());
        circular.setDescription(request.getDescription());
        circular.setDocumentUrl(request.getDocumentUrl());

        return CircularMapper.toResponse(circular);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        circularRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public CircularResponse toggleActive(Long id) {
        Circular circular = getOrThrow(id);
        circular.setActive(!circular.isActive());
        return CircularMapper.toResponse(circular);
    }

    @Override
    @Transactional(readOnly = true)
    public CircularResponse getById(Long id) {
        return CircularMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CircularResponse> getAllActive() {
        return circularRepository.findAllByActiveTrueOrderByDateDesc().stream()
                .map(CircularMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CircularResponse> getActiveByYear(Long academicYearId) {
        return circularRepository
                .findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(academicYearId).stream()
                .map(CircularMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CircularResponse> getByActiveAcademicYear() {

        AcademicYear activeYear = academicYearRepository.findByActiveTrue()
                .orElseThrow(() ->
                        new ResourceNotFoundException("No active academic year found."));

        return circularRepository
                .findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(activeYear.getId())
                .stream()
                .map(CircularMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CircularResponse> getAllForAdmin() {
        return circularRepository.findAllByOrderByDateDesc().stream()
                .map(CircularMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Circular getOrThrow(Long id) {
        return circularRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Circular", "id", id));
    }

    private AcademicYear getYearOrThrow(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicYear", "id", id));
    }
}