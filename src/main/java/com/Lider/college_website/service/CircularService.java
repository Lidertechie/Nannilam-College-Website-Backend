package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.CircularRequest;
import com.Lider.college_website.dto.response.CircularResponse;

import java.util.List;

public interface CircularService {

    CircularResponse create(CircularRequest request);
    CircularResponse update(Long id, CircularRequest request);
    void delete(Long id);
    CircularResponse toggleActive(Long id);
    CircularResponse getById(Long id);

    List<CircularResponse> getAllActive();
    List<CircularResponse> getActiveByYear(Long academicYearId);
    List<CircularResponse> getAllForAdmin();
    List<CircularResponse> getByActiveAcademicYear();
}