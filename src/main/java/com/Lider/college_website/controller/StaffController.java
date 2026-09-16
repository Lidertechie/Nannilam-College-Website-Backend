package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.StaffRequest;
import com.Lider.college_website.dto.response.StaffResponse;
import com.Lider.college_website.enums.StaffType;
import com.Lider.college_website.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/active-staffs")
    public ResponseEntity<List<StaffResponse>> getAllActiveStaff() {
        return ResponseEntity.ok(staffService.getAllActive());
    }


    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllForAdmin());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<StaffResponse> createStaff(@Valid @RequestBody StaffRequest request) {
        StaffResponse response = staffService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponse> updateStaff(
            @PathVariable Long id, @Valid @RequestBody StaffRequest request) {
        return ResponseEntity.ok(staffService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<StaffResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-course")
    public ResponseEntity<List<StaffResponse>> getByCourse(
            @RequestParam Long courseId) {

        return ResponseEntity.ok(staffService.getByCourse(courseId));
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<StaffResponse>> getByStaffType(
            @RequestParam StaffType staffType) {

        return ResponseEntity.ok(staffService.getByStaffType(staffType));
    }
}