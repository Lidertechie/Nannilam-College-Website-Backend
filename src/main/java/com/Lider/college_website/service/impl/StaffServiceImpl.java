package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.StaffRequest;
import com.Lider.college_website.dto.response.StaffResponse;
import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.enums.StaffType;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.StaffRepository;
import com.Lider.college_website.repository.SubjectAllocationRepository;
import com.Lider.college_website.service.StaffService;
import com.Lider.college_website.service.mapper.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final SubjectAllocationRepository subjectAllocationRepository;

    @Override
    @Transactional
    public StaffResponse create(StaffRequest request) {
        Staff staff = Staff.builder()
                .name(request.getName())
                .qualification(request.getQualification())
                .designation(request.getDesignation())
                .imageUrl(request.getImageUrl())
                .documentUrl(request.getDocumentUrl())
                .staffType(request.getStaffType())
                .build();

        Staff saved = staffRepository.save(staff);
        return StaffMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public StaffResponse update(Long id, StaffRequest request) {
        Staff staff = getStaffOrThrow(id);
        staff.setName(request.getName());
        staff.setQualification(request.getQualification());
        staff.setDesignation(request.getDesignation());
        staff.setImageUrl(request.getImageUrl());
        staff.setDocumentUrl(request.getDocumentUrl());
        staff.setStaffType(request.getStaffType());
        return StaffMapper.toResponse(staff); // dirty checking flushes on commit
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Staff staff = getStaffOrThrow(id);
        staffRepository.delete(staff);
    }

    @Override
    @Transactional
    public StaffResponse toggleActive(Long id) {
        Staff staff = getStaffOrThrow(id);
        staff.setActive(!staff.isActive());
        return StaffMapper.toResponse(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getById(Long id) {
        return StaffMapper.toResponse(getStaffOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getAllActive() {
        return staffRepository.findAllByActiveTrue().stream()
                .map(StaffMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getAllForAdmin() {
        return staffRepository.findAll().stream()
                .map(StaffMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Staff getStaffOrThrow(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Staff", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getByCourse(Long courseId) {

        return subjectAllocationRepository.findStaffByCourseId(courseId)
                .stream()
                .map(StaffMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponse> getByStaffType(StaffType staffType) {

        return staffRepository.findByStaffTypeAndActiveTrue(staffType)
                .stream()
                .map(StaffMapper::toResponse)
                .toList();
    }
}