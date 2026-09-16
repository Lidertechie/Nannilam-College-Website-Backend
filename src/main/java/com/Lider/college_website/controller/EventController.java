package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.EventRequest;
import com.Lider.college_website.dto.response.EventResponse;
import com.Lider.college_website.enums.EventStatus;
import com.Lider.college_website.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ---- Public read endpoints ----

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll(
            @RequestParam(required = false) EventStatus status) {

        List<EventResponse> result = status != null
                ? eventService.getActiveByStatus(status)
                : eventService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    // ---- Admin-only write endpoints ----

    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<EventResponse>> getAllForAdmin() {
        return ResponseEntity.ok(eventService.getAllForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<EventResponse> update(
            @PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<EventResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<List<EventResponse>> getCurrentEvents() {
        return ResponseEntity.ok(eventService.getCurrentEvents());
    }
}