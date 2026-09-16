package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.AddDivisionRequest;
import com.Lider.college_website.dto.request.DepartmentRequest;
import com.Lider.college_website.dto.response.DepartmentDivisionResponse;
import com.Lider.college_website.dto.response.DepartmentResponse;
import com.Lider.college_website.entity.Department;
import com.Lider.college_website.entity.DepartmentDivision;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.DepartmentDivisionRepository;
import com.Lider.college_website.repository.DepartmentRepository;
import com.Lider.college_website.service.DepartmentService;
import com.Lider.college_website.service.mapper.AcademicStructureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentDivisionRepository divisionRepository;

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw DuplicateResourceException.forField("Department", "name", request.getName());
        }

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Department saved = departmentRepository.save(department);

        List<DepartmentDivision> divisions = request.getDivisions().stream()
                .map(category -> DepartmentDivision.builder()
                        .department(saved)
                        .category(category)
                        .build())
                .map(divisionRepository::save)
                .collect(Collectors.toList());

        return AcademicStructureMapper.toResponse(saved, divisions);
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = getOrThrow(id);
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        // Note: divisions are NOT modified here — use addDivision()/removeDivision() explicitly.
        List<DepartmentDivision> divisions = divisionRepository.findAllByDepartmentId(id);
        return AcademicStructureMapper.toResponse(department, divisions);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departmentRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public DepartmentResponse toggleActive(Long id) {
        Department department = getOrThrow(id);
        department.setActive(!department.isActive());
        List<DepartmentDivision> divisions = divisionRepository.findAllByDepartmentId(id);
        return AcademicStructureMapper.toResponse(department, divisions);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {
        Department department = getOrThrow(id);
        List<DepartmentDivision> divisions = divisionRepository.findAllByDepartmentId(id);
        return AcademicStructureMapper.toResponse(department, divisions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllActive() {
        return departmentRepository.findAll().stream()
                .filter(Department::isActive)
                .map(dept -> AcademicStructureMapper.toResponse(
                        dept, divisionRepository.findAllByDepartmentId(dept.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllForAdmin() {
        return departmentRepository.findAll().stream()
                .map(dept -> AcademicStructureMapper.toResponse(
                        dept, divisionRepository.findAllByDepartmentId(dept.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentDivisionResponse addDivision(Long departmentId, AddDivisionRequest request) {
        Department department = getOrThrow(departmentId);

        if (divisionRepository.existsByDepartmentIdAndCategory(departmentId, request.getCategory())) {
            throw DuplicateResourceException.forField(
                    "DepartmentDivision", "category", request.getCategory());
        }

        DepartmentDivision division = DepartmentDivision.builder()
                .department(department)
                .category(request.getCategory())
                .build();

        return AcademicStructureMapper.toResponse(divisionRepository.save(division));
    }

    @Override
    @Transactional
    public void removeDivision(Long departmentId, Long divisionId) {
        DepartmentDivision division = divisionRepository.findById(divisionId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("DepartmentDivision", "id", divisionId));

        if (!division.getDepartment().getId().equals(departmentId)) {
            throw new ResourceNotFoundException(
                    "Division " + divisionId + " does not belong to department " + departmentId);
        }

        division.setActive(false); // soft-deactivate — courses under it remain intact for history
    }

    private Department getOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Department", "id", id));
    }
}