package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.SubjectRequest;
import com.Lider.college_website.dto.response.SubjectResponse;
import com.Lider.college_website.entity.Course;
import com.Lider.college_website.entity.Subject;
import com.Lider.college_website.entity.SubjectAllocation;
import com.Lider.college_website.exception.DuplicateResourceException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.CourseRepository;
import com.Lider.college_website.repository.SubjectAllocationRepository;
import com.Lider.college_website.repository.SubjectRepository;
import com.Lider.college_website.service.SubjectService;
import com.Lider.college_website.service.mapper.AcademicStructureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final SubjectAllocationRepository allocationRepository;

//    @Override
//    @Transactional
//    public SubjectResponse create(SubjectRequest request) {
//        Course course = getCourseOrThrow(request.getCourseId());
//
//        Subject subject = Subject.builder()
//                .course(course)
//                .subjectName(request.getSubjectName())
//                .build();
//
//        Subject saved = subjectRepository.save(subject);
//        return AcademicStructureMapper.toResponse(saved, Collections.emptyList());
//    }

//    @Override
//    @Transactional
//    public SubjectResponse update(Long id, SubjectRequest request) {
//        Subject subject = getOrThrow(id);
//
//        if (!subject.getCourse().getId().equals(request.getCourseId())) {
//            subject.setCourse(getCourseOrThrow(request.getCourseId()));
//        }
//        subject.setSubjectName(request.getSubjectName());
//
//        List<SubjectAllocation> allocations = allocationRepository.findAllBySubjectIdAndActiveTrue(id);
//        return AcademicStructureMapper.toResponse(subject, allocations);
//    }

    @Override
    @Transactional
    public SubjectResponse create(SubjectRequest request) {
        Course course = getCourseOrThrow(request.getCourseId());

        if (subjectRepository.existsByCourseIdAndSubjectNameIgnoreCase(
                request.getCourseId(), request.getSubjectName())) {
            throw DuplicateResourceException.forField("Subject", "subjectName", request.getSubjectName());
        }

        Subject subject = Subject.builder()
                .course(course)
                .subjectName(request.getSubjectName())
                .build();

        Subject saved = subjectRepository.save(subject);
        return AcademicStructureMapper.toResponse(saved, Collections.emptyList());
    }

    @Override
    @Transactional
    public SubjectResponse update(Long id, SubjectRequest request) {
        Subject subject = getOrThrow(id);

        if (subjectRepository.existsByCourseIdAndSubjectNameIgnoreCaseAndIdNot(
                request.getCourseId(), request.getSubjectName(), id)) {
            throw DuplicateResourceException.forField("Subject", "subjectName", request.getSubjectName());
        }

        if (!subject.getCourse().getId().equals(request.getCourseId())) {
            subject.setCourse(getCourseOrThrow(request.getCourseId()));
        }
        subject.setSubjectName(request.getSubjectName());

        List<SubjectAllocation> allocations = allocationRepository.findAllBySubjectIdAndActiveTrue(id);
        return AcademicStructureMapper.toResponse(subject, allocations);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        subjectRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public SubjectResponse toggleActive(Long id) {
        Subject subject = getOrThrow(id);
        subject.setActive(!subject.isActive());
        List<SubjectAllocation> allocations = allocationRepository.findAllBySubjectIdAndActiveTrue(id);
        return AcademicStructureMapper.toResponse(subject, allocations);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponse getById(Long id) {
        Subject subject = getOrThrow(id);
        List<SubjectAllocation> allocations = allocationRepository.findAllBySubjectIdAndActiveTrue(id);
        return AcademicStructureMapper.toResponse(subject, allocations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getByCourse(Long courseId) {
        return subjectRepository.findAllByCourseIdAndActiveTrue(courseId).stream()
                .map(s -> AcademicStructureMapper.toResponse(
                        s, allocationRepository.findAllBySubjectIdAndActiveTrue(s.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getByCourseForAdmin(Long courseId) {
        return subjectRepository.findAllByCourseId(courseId).stream()
                .map(s -> AcademicStructureMapper.toResponse(
                        s, allocationRepository.findAllBySubjectIdAndActiveTrue(s.getId())))
                .collect(Collectors.toList());
    }

    private Subject getOrThrow(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Subject", "id", id));
    }

    private Course getCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Course", "id", id));
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<SubjectResponse> getAll() {
//
//        return subjectRepository.findAllByActiveTrue().stream()
//                .map(subject -> AcademicStructureMapper.toResponse(
//                        subject,
//                        allocationRepository.findAllBySubjectIdAndActiveTrue(subject.getId())))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getAll() {

        return subjectRepository.findAllByActiveTrue().stream()
                .map(AcademicStructureMapper::toResponseWithoutAllocations)
                .collect(Collectors.toList());
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<SubjectResponse> getAllForAdmin() {
//
//        return subjectRepository.findAll().stream()
//                .map(subject -> AcademicStructureMapper.toResponse(
//                        subject,
//                        allocationRepository.findAllBySubjectIdAndActiveTrue(subject.getId())))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getAllForAdmin() {

        return subjectRepository.findAll().stream()
                .map(AcademicStructureMapper::toResponseWithoutAllocations)
                .collect(Collectors.toList());
    }
}