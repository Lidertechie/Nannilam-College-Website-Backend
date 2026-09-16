package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.ScholarshipOfficerRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerResponse;
import com.Lider.college_website.service.ScholarshipOfficerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship-officers")
@RequiredArgsConstructor
public class ScholarshipOfficerController {

    private final ScholarshipOfficerService officerService;

    @GetMapping
    public ResponseEntity<List<ScholarshipOfficerResponse>> getAll(
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long staffId) {

        if (groupId != null) {
            return ResponseEntity.ok(officerService.getActiveByGroup(groupId));
        }
        if (staffId != null) {
            return ResponseEntity.ok(officerService.getActiveByStaff(staffId));
        }
        return ResponseEntity.ok(officerService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScholarshipOfficerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(officerService.getById(id));
    }

    @GetMapping("/admin/all")
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<ScholarshipOfficerResponse>> getAllForAdmin() {
        return ResponseEntity.ok(officerService.getAllForAdmin());
    }

    @PostMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerResponse> create(
            @Valid @RequestBody ScholarshipOfficerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(officerService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerResponse> update(
            @PathVariable Long id, @Valid @RequestBody ScholarshipOfficerRequest request) {
        return ResponseEntity.ok(officerService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(officerService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        officerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}