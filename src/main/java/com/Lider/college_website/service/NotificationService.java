package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.NotificationRequest;
import com.Lider.college_website.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest request);
    NotificationResponse update(Long id, NotificationRequest request);
    void delete(Long id);
    NotificationResponse toggleActive(Long id);
    NotificationResponse getById(Long id);
    List<NotificationResponse> getForActiveAcademicYear();
    List<NotificationResponse> getAllActive();                        // public, all years
    List<NotificationResponse> getActiveByYear(Long academicYearId);  // public, filtered
    List<NotificationResponse> getAllForAdmin();                       // admin, includes inactive
}