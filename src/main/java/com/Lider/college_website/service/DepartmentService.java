package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.AddDivisionRequest;
import com.Lider.college_website.dto.request.DepartmentRequest;
import com.Lider.college_website.dto.response.DepartmentDivisionResponse;
import com.Lider.college_website.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse create(DepartmentRequest request);
    DepartmentResponse update(Long id, DepartmentRequest request); // name/description only; divisions untouched
    void delete(Long id);
    DepartmentResponse toggleActive(Long id);
    DepartmentResponse getById(Long id);
    List<DepartmentResponse> getAllActive();
    List<DepartmentResponse> getAllForAdmin();

    DepartmentDivisionResponse addDivision(Long departmentId, AddDivisionRequest request);
    void removeDivision(Long departmentId, Long divisionId); // soft-deactivate
}