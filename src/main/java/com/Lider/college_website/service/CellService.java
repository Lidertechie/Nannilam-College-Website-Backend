package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.CellRequest;
import com.Lider.college_website.dto.response.CellResponse;
import com.Lider.college_website.enums.CellCategory;

import java.util.List;

public interface CellService {

    CellResponse create(CellRequest request);
    CellResponse update(Long id, CellRequest request);
    void delete(Long id);
    CellResponse toggleActive(Long id);
    CellResponse getById(Long id);

    List<CellResponse> getAllActive();
    List<CellResponse> getActiveByCategory(CellCategory category);
    List<CellResponse> getAllForAdmin();
}