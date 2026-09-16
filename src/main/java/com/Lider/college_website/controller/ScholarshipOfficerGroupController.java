package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.ScholarshipOfficerGroupRequest;
import com.Lider.college_website.dto.response.ScholarshipOfficerGroupResponse;
import com.Lider.college_website.service.ScholarshipOfficerGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship-officer-groups")
@RequiredArgsConstructor
public class ScholarshipOfficerGroupController {

    private final ScholarshipOfficerGroupService groupService;

    @GetMapping
    public ResponseEntity<List<ScholarshipOfficerGroupResponse>> getAll() {
        return ResponseEntity.ok(groupService.getAllActive());
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<ScholarshipOfficerGroupResponse>> getAllForAdmin() {
        return ResponseEntity.ok(groupService.getAllForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerGroupResponse> create(
            @Valid @RequestBody ScholarshipOfficerGroupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerGroupResponse> update(
            @PathVariable Long id, @Valid @RequestBody ScholarshipOfficerGroupRequest request) {
        return ResponseEntity.ok(groupService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ScholarshipOfficerGroupResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}