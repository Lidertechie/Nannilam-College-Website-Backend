package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.ScholarshipOfficerRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerResponse;
import com.Lider.college_website.entity.ScholarshipOfficer;
import com.Lider.college_website.entity.ScholarshipOfficerGroup;
import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.ScholarshipOfficerGroupRepository;
import com.Lider.college_website.repository.ScholarshipOfficerRepository;
import com.Lider.college_website.repository.StaffRepository;
import com.Lider.college_website.service.ScholarshipOfficerService;
import com.Lider.college_website.service.mapper.ScholarshipOfficerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipOfficerServiceImpl implements ScholarshipOfficerService {

    private final ScholarshipOfficerRepository officerRepository;
    private final ScholarshipOfficerGroupRepository groupRepository;
    private final StaffRepository staffRepository;

    @Override
    @Transactional
    public ScholarshipOfficerResponse create(ScholarshipOfficerRequest request) {
        ScholarshipOfficerGroup group = getGroupOrThrow(request.getGroupId());
        Staff staff = getStaffOrThrow(request.getStaffId());

        ScholarshipOfficer officer = ScholarshipOfficer.builder()
                .group(group)
                .staff(staff)
                .role(request.getRole())
                .build();

        return ScholarshipOfficerMapper.toResponse(officerRepository.save(officer));
    }

    @Override
    @Transactional
    public ScholarshipOfficerResponse update(Long id, ScholarshipOfficerRequest request) {
        ScholarshipOfficer officer = getOrThrow(id);

        if (!officer.getGroup().getId().equals(request.getGroupId())) {
            officer.setGroup(getGroupOrThrow(request.getGroupId()));
        }
        if (!officer.getStaff().getId().equals(request.getStaffId())) {
            officer.setStaff(getStaffOrThrow(request.getStaffId()));
        }
        officer.setRole(request.getRole());

        return ScholarshipOfficerMapper.toResponse(officer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        officerRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public ScholarshipOfficerResponse toggleActive(Long id) {
        ScholarshipOfficer officer = getOrThrow(id);
        officer.setActive(!officer.isActive());
        return ScholarshipOfficerMapper.toResponse(officer);
    }

    @Override
    @Transactional(readOnly = true)
    public ScholarshipOfficerResponse getById(Long id) {
        return ScholarshipOfficerMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerResponse> getAllActive() {
        return officerRepository.findAllByActiveTrue().stream()
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerResponse> getActiveByGroup(Long groupId) {
        return officerRepository.findAllByGroupIdAndActiveTrue(groupId).stream()
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerResponse> getActiveByStaff(Long staffId) {
        return officerRepository.findAllByStaffIdAndActiveTrue(staffId).stream()
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipOfficerResponse> getAllForAdmin() {
        return officerRepository.findAll().stream()
                .map(ScholarshipOfficerMapper::toResponse)
                .collect(Collectors.toList());
    }

    private ScholarshipOfficer getOrThrow(Long id) {
        return officerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("ScholarshipOfficer", "id", id));
    }

    private ScholarshipOfficerGroup getGroupOrThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("ScholarshipOfficerGroup", "id", id));
    }

    private Staff getStaffOrThrow(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Staff", "id", id));
    }
}