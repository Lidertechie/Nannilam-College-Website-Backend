package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.CourseRequest;
import com.Lider.college_website.dto.response.CourseResponse;
import com.Lider.college_website.enums.CourseCategory;

import java.util.List;

public interface CourseService {

    CourseResponse create(CourseRequest request);
    CourseResponse update(Long id, CourseRequest request);
    void delete(Long id);
    CourseResponse toggleActive(Long id);
    CourseResponse getById(Long id);

    List<CourseResponse> getAllActive();
    List<CourseResponse> getActiveByCategory(CourseCategory category);
    List<CourseResponse> getAllForAdmin();
    List<CourseResponse> getByDivision(Long departmentDivisionId);
    List<CourseResponse> getByDivisionForAdmin(Long departmentDivisionId);
}