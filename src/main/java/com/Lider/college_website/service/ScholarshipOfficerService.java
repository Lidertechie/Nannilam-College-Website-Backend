package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.ScholarshipOfficerRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerResponse;

import java.util.List;

public interface ScholarshipOfficerService {
    ScholarshipOfficerResponse create(ScholarshipOfficerRequest request);
    ScholarshipOfficerResponse update(Long id, ScholarshipOfficerRequest request);
    void delete(Long id);
    ScholarshipOfficerResponse toggleActive(Long id);
    ScholarshipOfficerResponse getById(Long id);

    List<ScholarshipOfficerResponse> getAllActive();
    List<ScholarshipOfficerResponse> getActiveByGroup(Long groupId);
    List<ScholarshipOfficerResponse> getActiveByStaff(Long staffId);
    List<ScholarshipOfficerResponse> getAllForAdmin();
}