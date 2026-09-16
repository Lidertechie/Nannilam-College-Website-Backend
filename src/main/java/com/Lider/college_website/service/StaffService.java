package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.StaffRequest;
import com.Lider.college_website.dto.response.StaffResponse;
import com.Lider.college_website.enums.StaffType;

import java.util.List;

public interface StaffService {

    StaffResponse create(StaffRequest request);

    StaffResponse update(Long id, StaffRequest request);

    void delete(Long id);

    StaffResponse toggleActive(Long id);

    StaffResponse getById(Long id);

    List<StaffResponse> getAllActive();

    List<StaffResponse> getAllForAdmin();

    List<StaffResponse> getByCourse(Long courseId);

    List<StaffResponse> getByStaffType(StaffType staffType);
}