package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.AcademicYearRequest;
import com.Lider.college_website.dto.response.AcademicYearResponse;
import com.Lider.college_website.service.AcademicYearService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    @GetMapping
    public ResponseEntity<List<AcademicYearResponse>> getAll() {
        return ResponseEntity.ok(academicYearService.getAll());
    }
    @PostMapping("/create")
    public ResponseEntity<AcademicYearResponse> create(@Valid @RequestBody AcademicYearRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(academicYearService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearResponse> update(
            @PathVariable Long id, @Valid @RequestBody AcademicYearRequest request) {
        return ResponseEntity.ok(academicYearService.update(id, request));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<AcademicYearResponse> setActive(@PathVariable Long id) {
        return ResponseEntity.ok(academicYearService.setActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        academicYearService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
