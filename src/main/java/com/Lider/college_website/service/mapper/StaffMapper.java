package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.StaffResponse;
import com.Lider.college_website.entity.Staff;

public final class StaffMapper {

    private StaffMapper() {
        // utility class, no instances
    }

    public static StaffResponse toResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .name(staff.getName())
                .qualification(staff.getQualification())
                .designation(staff.getDesignation())
                .imageUrl(staff.getImageUrl())
                .documentUrl(staff.getDocumentUrl())
                .active(staff.isActive())
                .staffType(staff.getStaffType())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .build();
    }
}