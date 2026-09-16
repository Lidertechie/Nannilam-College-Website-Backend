package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.EventRequest;
import com.Lider.college_website.dto.response.EventResponse;
import com.Lider.college_website.enums.EventStatus;

import java.util.List;

public interface EventService {

    EventResponse create(EventRequest request);
    EventResponse update(Long id, EventRequest request);
    void delete(Long id);
    EventResponse toggleActive(Long id);
    EventResponse getById(Long id);
    List<EventResponse> getCurrentEvents();
    List<EventResponse> getAllActive();
    List<EventResponse> getActiveByStatus(EventStatus status);
    List<EventResponse> getAllForAdmin();
}