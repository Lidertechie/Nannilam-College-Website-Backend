package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.CommitteeRequest;
import com.Lider.college_website.dto.response.CommitteeResponse;

import java.util.List;

public interface CommitteeService {

    CommitteeResponse create(CommitteeRequest request);

    CommitteeResponse update(Long id, CommitteeRequest request);

    CommitteeResponse getById(Long id);

    List<CommitteeResponse> getAll();

    void delete(Long id);
}