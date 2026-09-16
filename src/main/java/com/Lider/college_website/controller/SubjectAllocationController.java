package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.SubjectAllocationRequest;
import com.Lider.college_website.dto.response.SubjectAllocationResponse;
import com.Lider.college_website.service.SubjectAllocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject-allocations")
@RequiredArgsConstructor
public class SubjectAllocationController {

    private final SubjectAllocationService allocationService;

//    @GetMapping
//    public ResponseEntity<List<SubjectAllocationResponse>> getBySubjectOrStaff(
//            @RequestParam(required = false) Long subjectId,
//            @RequestParam(required = false) Long staffId) {
//
//        if (subjectId != null) {
//            return ResponseEntity.ok(allocationService.getBySubject(subjectId));
//        }
//        if (staffId != null) {
//            return ResponseEntity.ok(allocationService.getByStaff(staffId));
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @GetMapping
    public ResponseEntity<List<SubjectAllocationResponse>> getAll(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long staffId) {

        if (subjectId != null) {
            return ResponseEntity.ok(allocationService.getBySubject(subjectId));
        }

        if (staffId != null) {
            return ResponseEntity.ok(allocationService.getByStaff(staffId));
        }

        return ResponseEntity.ok(allocationService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SubjectAllocationResponse> create(@Valid @RequestBody SubjectAllocationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(allocationService.create(request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SubjectAllocationResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(allocationService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        allocationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SubjectAllocationResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SubjectAllocationRequest request) {

        return ResponseEntity.ok(allocationService.update(id, request));
    }
}