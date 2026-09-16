package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.NaanMudhalvanSchemeRequestDTO;
import com.Lider.college_website.dto.response.NaanMudhalvanSchemeResponseDTO;
import com.Lider.college_website.entity.Department;
import com.Lider.college_website.entity.NaanMudhalvanScheme;
import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.entity.SubjectAllocation;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.NaanMudhalvanSchemeRepository;
import com.Lider.college_website.repository.StaffRepository;
import com.Lider.college_website.repository.SubjectAllocationRepository;
import com.Lider.college_website.service.NaanMudhalvanSchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NaanMudhalvanSchemeServiceImpl implements NaanMudhalvanSchemeService {

    private final NaanMudhalvanSchemeRepository repository;
    private final StaffRepository staffRepository;
    private final SubjectAllocationRepository subjectAllocationRepository;

    @Override
    @Transactional
    public NaanMudhalvanSchemeResponseDTO create(NaanMudhalvanSchemeRequestDTO requestDTO) {
        Staff staff = staffRepository.findById(requestDTO.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Staff not found with id: " + requestDTO.getStaffId()));

        NaanMudhalvanScheme entity = new NaanMudhalvanScheme();
        entity.setStaffId(staff.getId());
        entity.setCoordinatorName(staff.getName());        // auto from Staff
        entity.setQualification(staff.getQualification());  // auto from Staff

        // Walk the chain: Staff -> SubjectAllocation -> Subject -> Course -> DepartmentDivision -> Department
        Department department = resolveDepartmentForStaff(staff.getId());
        if (department != null) {
            entity.setDepartmentId(department.getId());
            entity.setDepartmentName(department.getName());
        }

        entity.setPosition(requestDTO.getPosition());       // manual

        // Auto fields
        entity.setVerified(true);
        entity.setAcademicYear(getCurrentAcademicYear());

        NaanMudhalvanScheme saved = repository.save(entity);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NaanMudhalvanSchemeResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NaanMudhalvanSchemeResponseDTO getById(Long id) {
        NaanMudhalvanScheme entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naan Mudhalvan Scheme record not found with id: " + id));
        return mapToResponseDTO(entity);
    }

    @Override
    @Transactional
    public NaanMudhalvanSchemeResponseDTO update(Long id, NaanMudhalvanSchemeRequestDTO requestDTO) {
        NaanMudhalvanScheme entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naan Mudhalvan Scheme record not found with id: " + id));

        if (!entity.getStaffId().equals(requestDTO.getStaffId())) {
            Staff staff = staffRepository.findById(requestDTO.getStaffId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Staff not found with id: " + requestDTO.getStaffId()));
            entity.setStaffId(staff.getId());
            entity.setCoordinatorName(staff.getName());
            entity.setQualification(staff.getQualification());

            Department department = resolveDepartmentForStaff(staff.getId());
            if (department != null) {
                entity.setDepartmentId(department.getId());
                entity.setDepartmentName(department.getName());
            }
        }

        entity.setPosition(requestDTO.getPosition());
        // verified and academicYear are left untouched on update

        NaanMudhalvanScheme updated = repository.save(entity);
        return mapToResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        NaanMudhalvanScheme entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naan Mudhalvan Scheme record not found with id: " + id));
        repository.delete(entity);
    }

    @Override
    @Transactional
    public NaanMudhalvanSchemeResponseDTO updateVerifiedStatus(Long id, boolean verified) {
        NaanMudhalvanScheme entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Naan Mudhalvan Scheme record not found with id: " + id));
        entity.setVerified(verified);
        NaanMudhalvanScheme updated = repository.save(entity);
        return mapToResponseDTO(updated);
    }

    // ---------- helpers ----------

    /**
     * Walks Staff -> SubjectAllocation -> Subject -> Course -> DepartmentDivision -> Department
     * Returns null if the staff has no active subject allocation (department stays empty;
     * doesn't block creating the record).
     */
    private Department resolveDepartmentForStaff(Long staffId) {
        Optional<SubjectAllocation> allocationOpt =
                subjectAllocationRepository.findFirstByStaff_IdAndActiveTrue(staffId);

        if (allocationOpt.isEmpty()) {
            return null;
        }

        SubjectAllocation allocation = allocationOpt.get();
        return allocation.getSubject()
                .getCourse()
                .getDepartmentDivision()
                .getDepartment();
    }

    private String getCurrentAcademicYear() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        int startYear = (month >= 6) ? year : year - 1;
        int endYearShort = (startYear + 1) % 100;

        return String.format("%d-%02d", startYear, endYearShort);
    }

    private NaanMudhalvanSchemeResponseDTO mapToResponseDTO(NaanMudhalvanScheme entity) {
        return new NaanMudhalvanSchemeResponseDTO(
                entity.getId(),
                entity.getStaffId(),
                entity.getCoordinatorName(),
                entity.getQualification(),
                entity.getDepartmentId(),
                entity.getDepartmentName(),
                entity.getPosition(),
                entity.getVerified(),
                entity.getAcademicYear()
        );
    }
}