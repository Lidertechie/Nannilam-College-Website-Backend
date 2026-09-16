package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.SubjectAllocationRequest;
import com.Lider.college_website.dto.response.SubjectAllocationResponse;

import java.util.List;

public interface SubjectAllocationService {
    SubjectAllocationResponse create(SubjectAllocationRequest request);
    void delete(Long id);
    SubjectAllocationResponse toggleActive(Long id);
    List<SubjectAllocationResponse> getBySubject(Long subjectId);
    List<SubjectAllocationResponse> getByStaff(Long staffId);
    List<SubjectAllocationResponse> getAll();
    SubjectAllocationResponse update(Long id, SubjectAllocationRequest request);
}