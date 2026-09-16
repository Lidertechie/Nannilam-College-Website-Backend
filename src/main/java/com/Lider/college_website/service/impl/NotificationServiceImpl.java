package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.NotificationRequest;
import com.Lider.college_website.dto.response.NotificationResponse;
import com.Lider.college_website.entity.AcademicYear;
import com.Lider.college_website.entity.Notification;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.AcademicYearRepository;
import com.Lider.college_website.repository.NotificationRepository;
import com.Lider.college_website.service.NotificationService;
import com.Lider.college_website.service.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public NotificationResponse create(NotificationRequest request) {
        AcademicYear year = getYearOrThrow(request.getAcademicYearId());

        Notification notification = Notification.builder()
                .title(request.getTitle())
                .date(request.getDate())
                .description(request.getDescription())
                .academicYear(year)
                .imageUrl(request.getImageUrl())
                .build();

        return NotificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public NotificationResponse update(Long id, NotificationRequest request) {
        Notification notification = getOrThrow(id);

        if (!notification.getAcademicYear().getId().equals(request.getAcademicYearId())) {
            notification.setAcademicYear(getYearOrThrow(request.getAcademicYearId()));
        }

        notification.setTitle(request.getTitle());
        notification.setDate(request.getDate());
        notification.setDescription(request.getDescription());
        notification.setImageUrl(request.getImageUrl());

        return NotificationMapper.toResponse(notification);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        notificationRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public NotificationResponse toggleActive(Long id) {
        Notification notification = getOrThrow(id);
        notification.setActive(!notification.isActive());
        return NotificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getForActiveAcademicYear() {

        AcademicYear activeYear = academicYearRepository.findByActiveTrue()
                .orElseThrow(() -> new ResourceNotFoundException("No active academic year found"));

        return notificationRepository
                .findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(activeYear.getId())
                .stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getById(Long id) {
        return NotificationMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllActive() {
        return notificationRepository.findAllByActiveTrueOrderByDateDesc().stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getActiveByYear(Long academicYearId) {
        return notificationRepository
                .findAllByAcademicYearIdAndActiveTrueOrderByDateDesc(academicYearId).stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllForAdmin() {
        return notificationRepository.findAllByOrderByDateDesc().stream()
                .map(NotificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Notification getOrThrow(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Notification", "id", id));
    }

    private AcademicYear getYearOrThrow(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("AcademicYear", "id", id));
    }
}