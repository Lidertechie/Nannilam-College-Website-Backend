package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.ScholarshipOfficerGroupRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerGroupResponse;

import java.util.List;

public interface ScholarshipOfficerGroupService {
    ScholarshipOfficerGroupResponse create(ScholarshipOfficerGroupRequest request);
    ScholarshipOfficerGroupResponse update(Long id, ScholarshipOfficerGroupRequest request);
    void delete(Long id);
    ScholarshipOfficerGroupResponse toggleActive(Long id);
    List<ScholarshipOfficerGroupResponse> getAllActive();
    List<ScholarshipOfficerGroupResponse> getAllForAdmin();
}