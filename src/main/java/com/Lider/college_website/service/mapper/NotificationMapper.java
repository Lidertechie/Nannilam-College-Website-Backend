package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.NotificationResponse;
import com.Lider.college_website.entity.Notification;

public final class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .date(notification.getDate())
                .description(notification.getDescription())
                .academicYearId(notification.getAcademicYear().getId())
                .academicYearLabel(notification.getAcademicYear().getYearLabel())
                .imageUrl(notification.getImageUrl())
                .active(notification.isActive())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}