package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.EventRequest;
import com.Lider.college_website.dto.response.EventResponse;
import com.Lider.college_website.entity.Event;
import com.Lider.college_website.enums.EventStatus;
import com.Lider.college_website.exception.InvalidDateRangeException;
import com.Lider.college_website.exception.ResourceNotFoundException;
import com.Lider.college_website.repository.EventRepository;
import com.Lider.college_website.service.EventService;
import com.Lider.college_website.service.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponse create(EventRequest request) {
        validateDateRange(request);

        Event event = Event.builder()
                .eventTitle(request.getEventTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .venue(request.getVenue())
                .description(request.getDescription())
                .build();

        return EventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventResponse update(Long id, EventRequest request) {
        validateDateRange(request);

        Event event = getOrThrow(id);
        event.setEventTitle(request.getEventTitle());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setVenue(request.getVenue());
        event.setDescription(request.getDescription());

        return EventMapper.toResponse(event);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventRepository.delete(getOrThrow(id));
    }

    @Override
    @Transactional
    public EventResponse toggleActive(Long id) {
        Event event = getOrThrow(id);
        event.setActive(!event.isActive());
        return EventMapper.toResponse(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getById(Long id) {
        return EventMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllActive() {
        return eventRepository.findAllByActiveTrueOrderByStartDateAsc().stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getActiveByStatus(EventStatus status) {
        return eventRepository.findAllByActiveTrueOrderByStartDateAsc().stream()
                .map(EventMapper::toResponse)
                .filter(e -> e.getStatus() == status) // computed-status filtering happens here
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllForAdmin() {
        return eventRepository.findAllByOrderByStartDateAsc().stream()
                .map(EventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getCurrentEvents() {

        return eventRepository.findAllByActiveTrueOrderByStartDateAsc()
                .stream()
                .map(EventMapper::toResponse)
                .filter(event ->
                        event.getStatus() == EventStatus.UPCOMING
                                || event.getStatus() == EventStatus.ONGOING)
                .collect(Collectors.toList());
    }

    private void validateDateRange(EventRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidDateRangeException("End date cannot be before start date");
        }
    }

    private Event getOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Event", "id", id));
    }
}