package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.CellRequest;
import com.Lider.college_website.dto.response.CellResponse;
import com.Lider.college_website.entity.Cell;
import com.Lider.college_website.entity.Course;
import com.Lider.college_website.entity.Staff;
import com.Lider.college_website.enums.CellCategory;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.CellRepository;
import com.Lider.college_website.repository.CourseRepository;
import com.Lider.college_website.repository.StaffRepository;
import com.Lider.college_website.service.CellService;
import com.Lider.college_website.service.mapper.CellMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CellServiceImpl implements CellService {

    private final CellRepository cellRepository;
    private final StaffRepository staffRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public CellResponse create(CellRequest request) {

        if (cellRepository.existsByStaffIdAndCategory(
                request.getStaffId(),
                request.getCategory())) {

            throw new IllegalArgumentException(
                    "This staff is already assigned to the selected category.");
        }

        Staff staff = getStaffOrThrow(request.getStaffId());

        Course course = null;

        if (request.getCourseId() != null) {
            course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() ->
                            ResourceNotFoundException.forEntity(
                                    "Course",
                                    "id",
                                    request.getCourseId()
                            )
                    );
        }

        Cell cell = Cell.builder()
                .category(request.getCategory())
                .course(course)
                .staff(staff)
                .build();

        return CellMapper.toResponse(cellRepository.save(cell));
    }

    @Override
    @Transactional
    public CellResponse update(Long id, CellRequest request) {

        if (cellRepository.existsByStaffIdAndCategory(
                request.getStaffId(),
                request.getCategory())) {

            throw new IllegalArgumentException(
                    "This staff is already assigned to the selected category.");
        }

        Cell cell = getOrThrow(id);

        if (!cell.getStaff().getId().equals(request.getStaffId())) {
            cell.setStaff(getStaffOrThrow(request.getStaffId()));
        }

        if (!cell.getCourse().getId().equals(request.getCourseId())) {

            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() ->
                            ResourceNotFoundException.forEntity(
                                    "Course",
                                    "id",
                                    request.getCourseId()
                            ));

            cell.setCourse(course);
        }

        cell.setCategory(request.getCategory());

        return CellMapper.toResponse(cell);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cellRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public CellResponse toggleActive(Long id) {
        Cell cell = getOrThrow(id);
        cell.setActive(!cell.isActive());
        return CellMapper.toResponse(cell);
    }

    @Override
    @Transactional(readOnly = true)
    public CellResponse getById(Long id) {
        return CellMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CellResponse> getAllActive() {
        return cellRepository.findAllByActiveTrue().stream()
                .map(CellMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CellResponse> getActiveByCategory(CellCategory category) {
        return cellRepository.findAllByCategoryAndActiveTrue(category).stream()
                .map(CellMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CellResponse> getAllForAdmin() {
        return cellRepository.findAll().stream()
                .map(CellMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Cell getOrThrow(Long id) {
        return cellRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Cell", "id", id));
    }

    private Staff getStaffOrThrow(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Staff", "id", id));
    }
}