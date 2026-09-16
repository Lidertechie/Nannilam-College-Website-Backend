package com.Lider.college_website.service.mapper;

import com.Lider.college_website.dto.response.EventResponse;
import com.Lider.college_website.entity.Event;

public final class EventMapper {

    private EventMapper() {}

    public static EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .eventTitle(event.getEventTitle())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .venue(event.getVenue())
                .description(event.getDescription())
                .status(event.getStatus()) // computed here, fresh every call
                .active(event.isActive())
                .createdAt(event.getCreatedAt())
                .build();
    }
}