package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.ScholarshipOfficerGroupRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerGroupResponse;
import com.Lider.college_website.entity.ScholarshipOfficerGroup;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.ScholarshipOfficerGroupRepository;
import com.Lider.college_website.service.ScholarshipOfficerGroupService;
import com.Lider.college_website.service.mapper.ScholarshipOfficerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipOfficerGroupServiceImpl implements ScholarshipOfficerGroupService {

    private final ScholarshipOfficerGroupRepository groupRepository;

    @Override
    @Transactional
    public ScholarshipOfficerGroupResponse create(ScholarshipOfficerGroupRequest request) {
        if (groupRepository.existsByName(request.getName())) {
            throw DuplicateResourceException.forField("ScholarshipOfficerGroup", "name", request.getName());
        }
        ScholarshipOfficerGroup group = ScholarshipOfficerGroup.builder()
                .name(request.getName())
                .build();
        return ScholarshipOfficerMapper.toResponse(groupRepository.save(group));
    }

    @Override
    @Transactional
    public ScholarshipOfficerGroupResponse update(Long id, ScholarshipOfficerGroupRequest request) {
        ScholarshipOfficerGroup group = getOrThrow(id);
        group.setName(request.getName());
        return ScholarshipOfficerMapper.toResponse(group);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        groupRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public ScholarshipOfficerGroupResponse toggleActive(Long id) {
        ScholarshipOfficerGroup group = getOrThrow(id);
        group.setActive(!group.isActive());
        return ScholarshipOfficerMapper.toResponse(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerGroupResponse> getAllActive() {
        return groupRepository.findAll().stream()
                .filter(ScholarshipOfficerGroup::isActive)
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerGroupResponse> getAllForAdmin() {
        return groupRepository.findAll().stream()
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    private ScholarshipOfficerGroup getOrThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("ScholarshipOfficerGroup", "id", id));
    }
}