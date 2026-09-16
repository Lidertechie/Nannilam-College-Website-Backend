package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.NaanMudhalvanSchemeRequestDTO;
import com.Lider.college_website.dto.response.NaanMudhalvanSchemeResponseDTO;
import com.Lider.college_website.service.NaanMudhalvanSchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/naan-mudhalvan-scheme")
@RequiredArgsConstructor
public class NaanMudhalvanSchemeController {

    private final NaanMudhalvanSchemeService service;

    // Create - client sends only coordinatorName, qualification, department, position
    @PostMapping
    public ResponseEntity<NaanMudhalvanSchemeResponseDTO> create(
            @RequestBody NaanMudhalvanSchemeRequestDTO requestDTO) {
        NaanMudhalvanSchemeResponseDTO response = service.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NaanMudhalvanSchemeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NaanMudhalvanSchemeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NaanMudhalvanSchemeResponseDTO> update(
            @PathVariable Long id,
            @RequestBody NaanMudhalvanSchemeRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Optional: admin toggles verified badge separately, body: {"verified": true}
    @PatchMapping("/{id}/verify")
    public ResponseEntity<NaanMudhalvanSchemeResponseDTO> updateVerifiedStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        boolean verified = body.getOrDefault("verified", true);
        return ResponseEntity.ok(service.updateVerifiedStatus(id, verified));
    }
}