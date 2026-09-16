package com.Lider.college_website.dto.response;

import com.Lider.college_website.enums.EventStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventResponse {
    private Long id;
    private String eventTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String venue;
    private String description;
    private EventStatus status; // computed at mapping time
    private boolean active;
    private LocalDateTime createdAt;
}