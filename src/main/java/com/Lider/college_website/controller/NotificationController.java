package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.NotificationRequest;
import com.Lider.college_website.dto.response.NotificationResponse;
import com.Lider.college_website.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/by-academic-year")
    public ResponseEntity<List<NotificationResponse>> getAll(
            @RequestParam(required = false) Long academicYearId) {

        List<NotificationResponse> result = academicYearId != null
                ? notificationService.getActiveByYear(academicYearId)
                : notificationService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAllForAdmin());
    }

    @PostMapping("/create")
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(
            @PathVariable Long id, @Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<NotificationResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-academic-year")
    public ResponseEntity<List<NotificationResponse>> getForActiveAcademicYear() {
        return ResponseEntity.ok(notificationService.getForActiveAcademicYear());
    }
}