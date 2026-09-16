package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.CommitteeRequest;
import com.Lider.college_website.dto.response.CommitteeResponse;
import com.Lider.college_website.entity.Committee;
import com.Lider.college_website.repository.CommitteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommitteeServiceImpl implements CommitteeService {

    private final CommitteeRepository committeeRepository;

    @Override
    public CommitteeResponse create(CommitteeRequest request) {

        Committee committee = new Committee();
        committee.setTitle(request.getTitle());
        committee.setFileUrl(request.getFileUrl());

        committee = committeeRepository.save(committee);

        return mapToResponse(committee);
    }

    @Override
    public CommitteeResponse update(Long id, CommitteeRequest request) {

        Committee committee = committeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Committee not found with id: " + id));

        committee.setTitle(request.getTitle());
        committee.setFileUrl(request.getFileUrl());

        committee = committeeRepository.save(committee);

        return mapToResponse(committee);
    }

    @Override
    public CommitteeResponse getById(Long id) {
        Committee committee = committeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Committee not found with id: " + id));
        return mapToResponse(committee);
    }

    @Override
    public List<CommitteeResponse> getAll() {
        return committeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Committee committee = committeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Committee not found with id: " + id));
        committeeRepository.delete(committee);
    }

    private CommitteeResponse mapToResponse(Committee committee) {
        CommitteeResponse response = new CommitteeResponse();
        response.setId(committee.getId());
        response.setTitle(committee.getTitle());
        response.setFileUrl(committee.getFileUrl());
        return response;
    }
}