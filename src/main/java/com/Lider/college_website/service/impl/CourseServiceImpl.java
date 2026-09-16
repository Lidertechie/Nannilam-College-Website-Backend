package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.CourseRequest;
import com.Lider.college_website.dto.response.CourseResponse;
import com.Lider.college_website.entity.Course;
import com.Lider.college_website.entity.DepartmentDivision;
import com.Lider.college_website.enums.CourseCategory;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.CourseRepository;
import com.Lider.college_website.repository.DepartmentDivisionRepository;
import com.Lider.college_website.service.CourseService;
import com.Lider.college_website.service.mapper.AcademicStructureMapper;
import com.Lider.college_website.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentDivisionRepository divisionRepository;

//    @Override
//    @Transactional
//    public CourseResponse create(CourseRequest request) {
//        DepartmentDivision division = getDivisionOrThrow(request.getDepartmentDivisionId());
//
//        Course course = Course.builder()
//                .category(request.getCategory())
//                .courseName(request.getCourseName())
//                .mediumOfInstruction(request.getMediumOfInstruction())
//                .departmentDivision(division)
//                .build();
//
//        return CourseMapper.toResponse(courseRepository.save(course));
//    }
//
//    @Override
//    @Transactional
//    public CourseResponse update(Long id, CourseRequest request) {
//        Course course = getOrThrow(id);
//
//        if (!course.getDepartmentDivision().getId().equals(request.getDepartmentDivisionId())) {
//            course.setDepartmentDivision(getDivisionOrThrow(request.getDepartmentDivisionId()));
//        }
//
//        course.setCategory(request.getCategory());
//        course.setCourseName(request.getCourseName());
//        course.setMediumOfInstruction(request.getMediumOfInstruction());
//
//        return CourseMapper.toResponse(course);
//    }

    @Override
    @Transactional
    public CourseResponse update(Long id, CourseRequest request) {
        Course course = getOrThrow(id);

        if (courseRepository.existsByDepartmentDivisionIdAndCourseNameIgnoreCaseAndIdNot(
                request.getDepartmentDivisionId(), request.getCourseName(), id)) {
            throw DuplicateResourceException.forField("Course", "courseName", request.getCourseName());
        }

        if (!course.getDepartmentDivision().getId().equals(request.getDepartmentDivisionId())) {
            course.setDepartmentDivision(getDivisionOrThrow(request.getDepartmentDivisionId()));
        }

        course.setCategory(request.getCategory());
        course.setCourseName(request.getCourseName());
        course.setMediumOfInstruction(request.getMediumOfInstruction());

        return AcademicStructureMapper.toResponse(course);
    }

    @Override
    @Transactional
    public CourseResponse create(CourseRequest request) {
        DepartmentDivision division = getDivisionOrThrow(request.getDepartmentDivisionId());

        if (courseRepository.existsByDepartmentDivisionIdAndCourseNameIgnoreCase(
                request.getDepartmentDivisionId(), request.getCourseName())) {
            throw DuplicateResourceException.forField("Course", "courseName", request.getCourseName());
        }

        Course course = Course.builder()
                .category(request.getCategory())
                .courseName(request.getCourseName())
                .mediumOfInstruction(request.getMediumOfInstruction())
                .departmentDivision(division)
                .build();

        return AcademicStructureMapper.toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public CourseResponse toggleActive(Long id) {
        Course course = getOrThrow(id);
        course.setActive(!course.isActive());
        return CourseMapper.toResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        return CourseMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllActive() {
        return courseRepository.findAllByActiveTrueOrderByCourseNameAsc().stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getActiveByCategory(CourseCategory category) {
        return courseRepository.findAllByCategoryAndActiveTrueOrderByCourseNameAsc(category).stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllForAdmin() {
        return courseRepository.findAllByOrderByCourseNameAsc().stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Course getOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Course", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByDivision(Long departmentDivisionId) {
        return courseRepository.findAllByDepartmentDivisionIdAndActiveTrue(departmentDivisionId).stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getByDivisionForAdmin(Long departmentDivisionId) {
        return courseRepository.findAllByDepartmentDivisionId(departmentDivisionId).stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }

    private DepartmentDivision getDivisionOrThrow(Long id) {
        return divisionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("DepartmentDivision", "id", id));
    }
}