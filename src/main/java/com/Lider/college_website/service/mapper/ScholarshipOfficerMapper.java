package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.ScholarshipOfficerGroupResponse;
import com.Lider.college_website.dto.response.ScholarshipOfficerResponse;
import com.Lider.college_website.entity.ScholarshipOfficer;
import com.Lider.college_website.entity.ScholarshipOfficerGroup;

public final class ScholarshipOfficerMapper {

    private ScholarshipOfficerMapper() {}

    public static ScholarshipOfficerGroupResponse toResponse(ScholarshipOfficerGroup group) {
        return ScholarshipOfficerGroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .active(group.isActive())
                .build();
    }

    public static ScholarshipOfficerResponse toResponse(ScholarshipOfficer officer) {
        return ScholarshipOfficerResponse.builder()
                .id(officer.getId())
                .role(officer.getRole())
                .active(officer.isActive())
                .groupId(officer.getGroup().getId())
                .groupName(officer.getGroup().getName())
                .staffId(officer.getStaff().getId())
                .staffName(officer.getStaff().getName())
                .staffDesignation(officer.getStaff().getDesignation())
                .build();
    }
}