package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.SubjectAllocationRequest;
import com.Lider.college_website.dto.response.SubjectAllocationResponse;
import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.entity.Subject;
import com.Lider.college_website.entity.SubjectAllocation;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.StaffRepository;
import com.Lider.college_website.repository.SubjectAllocationRepository;
import com.Lider.college_website.repository.SubjectRepository;
import com.Lider.college_website.service.SubjectAllocationService;
import com.Lider.college_website.service.mapper.AcademicStructureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectAllocationServiceImpl implements SubjectAllocationService {

    private final SubjectAllocationRepository allocationRepository;
    private final SubjectRepository subjectRepository;
    private final StaffRepository staffRepository;

    @Override
    @Transactional
    public SubjectAllocationResponse create(SubjectAllocationRequest request) {
        if (allocationRepository.existsBySubjectIdAndStaffId(request.getSubjectId(), request.getStaffId())) {
            throw new DuplicateResourceException(
                    "This staff member is already allocated to this subject");
        }

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Subject", "id", request.getSubjectId()));
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Staff", "id", request.getStaffId()));

        SubjectAllocation allocation = SubjectAllocation.builder()
                .subject(subject)
                .staff(staff)
                .build();

        return AcademicStructureMapper.toResponse(allocationRepository.save(allocation));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        allocationRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public SubjectAllocationResponse toggleActive(Long id) {
        SubjectAllocation allocation = getOrThrow(id);
        allocation.setActive(!allocation.isActive());
        return AcademicStructureMapper.toResponse(allocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAllocationResponse> getBySubject(Long subjectId) {
        return allocationRepository.findAllBySubjectIdAndActiveTrue(subjectId).stream()
                .map(AcademicStructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAllocationResponse> getAll() {
        return allocationRepository.findAll()
                .stream()
                .map(AcademicStructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectAllocationResponse> getByStaff(Long staffId) {
        return allocationRepository.findAllByStaffIdAndActiveTrue(staffId).stream()
                .map(AcademicStructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    private SubjectAllocation getOrThrow(Long id) {
        return allocationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("SubjectAllocation", "id", id));
    }

    @Override
    @Transactional
    public SubjectAllocationResponse update(Long id, SubjectAllocationRequest request) {

        SubjectAllocation allocation = getOrThrow(id);

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        ResourceNotFoundException.forEntity("Subject", "id", request.getSubjectId()));

        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() ->
                        ResourceNotFoundException.forEntity("Staff", "id", request.getStaffId()));

        // Prevent duplicate subject-staff combinations
        allocationRepository.findBySubjectIdAndStaffId(
                        request.getSubjectId(),
                        request.getStaffId())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "This staff member is already allocated to this subject");
                    }
                });

        allocation.setSubject(subject);
        allocation.setStaff(staff);

        return AcademicStructureMapper.toResponse(
                allocationRepository.save(allocation));
    }
}