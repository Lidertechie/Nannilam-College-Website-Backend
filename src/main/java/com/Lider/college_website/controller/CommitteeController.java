package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.CommitteeRequest;
import com.Lider.college_website.dto.response.CommitteeResponse;
import com.Lider.college_website.service.CommitteeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/committees")
@RequiredArgsConstructor
public class CommitteeController {

    private final CommitteeService committeeService;

    @PostMapping
    public ResponseEntity<CommitteeResponse> createCommittee(
            @Valid @RequestBody CommitteeRequest request) {

        return new ResponseEntity<>(
                committeeService.create(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommitteeResponse> updateCommittee(
            @PathVariable Long id,
            @Valid @RequestBody CommitteeRequest request) {

        return ResponseEntity.ok(committeeService.update(id, request));
    }

    @GetMapping
    public ResponseEntity<List<CommitteeResponse>> getAllCommittees() {
        return ResponseEntity.ok(committeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommitteeResponse> getCommitteeById(@PathVariable Long id) {
        return ResponseEntity.ok(committeeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommittee(@PathVariable Long id) {
        committeeService.delete(id);
        return ResponseEntity.ok("Committee deleted successfully.");
    }
}