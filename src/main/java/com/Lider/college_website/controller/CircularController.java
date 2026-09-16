package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.CircularRequest;
import com.Lider.college_website.dto.response.CircularResponse;
import com.Lider.college_website.service.CircularService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/circulars")
@RequiredArgsConstructor
public class CircularController {

    private final CircularService circularService;

    @GetMapping
    public ResponseEntity<List<CircularResponse>> getAll(
            @RequestParam(required = false) Long academicYearId) {

        List<CircularResponse> result = academicYearId != null
                ? circularService.getActiveByYear(academicYearId)
                : circularService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CircularResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(circularService.getById(id));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<CircularResponse>> getAllForAdmin() {
        return ResponseEntity.ok(circularService.getAllForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CircularResponse> create(@Valid @RequestBody CircularRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(circularService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CircularResponse> update(
            @PathVariable Long id, @Valid @RequestBody CircularRequest request) {
        return ResponseEntity.ok(circularService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CircularResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(circularService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        circularService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-academic-year")
    public ResponseEntity<List<CircularResponse>> getByActiveAcademicYear() {
        return ResponseEntity.ok(circularService.getByActiveAcademicYear());
    }
}