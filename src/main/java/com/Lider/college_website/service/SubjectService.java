package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.SubjectRequest;
import com.Lider.college_website.dto.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectResponse create(SubjectRequest request);
    SubjectResponse update(Long id, SubjectRequest request);
    void delete(Long id);
    SubjectResponse toggleActive(Long id);
    SubjectResponse getById(Long id);
    List<SubjectResponse> getByCourse(Long courseId);
    List<SubjectResponse> getByCourseForAdmin(Long courseId);
    List<SubjectResponse> getAll();
    List<SubjectResponse> getAllForAdmin();
}