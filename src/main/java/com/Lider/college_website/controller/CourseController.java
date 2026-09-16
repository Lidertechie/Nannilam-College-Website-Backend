package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.CourseRequest;
import com.Lider.college_website.dto.response.CourseResponse;
import com.Lider.college_website.enums.CourseCategory;
import com.Lider.college_website.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/category")
    public ResponseEntity<List<CourseResponse>> getAll(
            @RequestParam(required = false) CourseCategory category) {

        List<CourseResponse> result = category != null
                ? courseService.getActiveByCategory(category)
                : courseService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll() {
        return ResponseEntity.ok(courseService.getAllForAdmin());
    }

    @GetMapping("/by-divisions")
    public ResponseEntity<List<CourseResponse>> getByDivision(@RequestParam Long departmentDivisionId) {
        return ResponseEntity.ok(courseService.getByDivision(departmentDivisionId));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<CourseResponse>> getByDivisionForAdmin(@RequestParam Long departmentDivisionId) {
        return ResponseEntity.ok(courseService.getByDivisionForAdmin(departmentDivisionId));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(
            @PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<CourseResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}