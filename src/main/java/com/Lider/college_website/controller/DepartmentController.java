package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.AddDivisionRequest;
import com.Lider.college_website.dto.request.DepartmentRequest;
import com.Lider.college_website.dto.response.DepartmentDivisionResponse;
import com.Lider.college_website.dto.response.DepartmentResponse;
import com.Lider.college_website.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<DepartmentResponse>> getAllForAdmin() {
        return ResponseEntity.ok(departmentService.getAllForAdmin());
    }

    @PostMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<DepartmentResponse> update(
            @PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<DepartmentResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/divisions")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<DepartmentDivisionResponse> addDivision(
            @PathVariable Long id, @Valid @RequestBody AddDivisionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.addDivision(id, request));
    }

    @DeleteMapping("/{id}/divisions/{divisionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> removeDivision(
            @PathVariable Long id, @PathVariable Long divisionId) {
        departmentService.removeDivision(id, divisionId);
        return ResponseEntity.noContent().build();
    }
}