package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.AcademicYearRequest;
import com.Lider.college_website.dto.response.AcademicYearResponse;

import java.util.List;

public interface AcademicYearService {
    AcademicYearResponse create(AcademicYearRequest request);
    AcademicYearResponse update(Long id, AcademicYearRequest request);
    void delete(Long id);
    AcademicYearResponse setActive(Long id);
    List<AcademicYearResponse> getAll();
}