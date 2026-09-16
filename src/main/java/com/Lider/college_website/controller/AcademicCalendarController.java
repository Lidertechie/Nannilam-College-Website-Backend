package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.AcademicCalendarFileRequest;
import com.Lider.college_website.dto.request.AcademicCalendarRequest;
import com.Lider.college_website.dto.response.AcademicCalendarResponse;
import com.Lider.college_website.service.AcademicCalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-calendars")
@RequiredArgsConstructor
public class AcademicCalendarController {

    private final AcademicCalendarService calendarService;

    // ---- Public read endpoints ----

    @GetMapping
    public ResponseEntity<List<AcademicCalendarResponse>> getAll(
            @RequestParam(required = false) Long academicYearId) {

        List<AcademicCalendarResponse> result = academicYearId != null
                ? calendarService.getActiveByYear(academicYearId)
                : calendarService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicCalendarResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getById(id));
    }

    // ---- Admin-only write endpoints ----

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AcademicCalendarResponse>> getAllForAdmin() {
        return ResponseEntity.ok(calendarService.getAllForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AcademicCalendarResponse> createTopic(
            @Valid @RequestBody AcademicCalendarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.createTopic(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AcademicCalendarResponse> updateTopic(
            @PathVariable Long id, @Valid @RequestBody AcademicCalendarRequest request) {
        return ResponseEntity.ok(calendarService.updateTopic(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AcademicCalendarResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        calendarService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/files")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AcademicCalendarResponse> addFile(
            @PathVariable Long id, @Valid @RequestBody AcademicCalendarFileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.addFile(id, request));
    }

    @DeleteMapping("/{calendarId}/files/{fileId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long calendarId, @PathVariable Long fileId) {
        calendarService.deleteFile(calendarId, fileId);
        return ResponseEntity.noContent().build();
    }
}