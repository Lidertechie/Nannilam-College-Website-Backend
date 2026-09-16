package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.CellRequest;
import com.Lider.college_website.dto.response.CellResponse;
import com.Lider.college_website.enums.CellCategory;
import com.Lider.college_website.service.CellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cells")
@RequiredArgsConstructor
public class CellController {

    private final CellService cellService;

    // ---- Public read endpoints ----

    @GetMapping
    public ResponseEntity<List<CellResponse>> getAll(
            @RequestParam(required = false) CellCategory category) {

        List<CellResponse> result = category != null
                ? cellService.getActiveByCategory(category)
                : cellService.getAllActive();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CellResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cellService.getById(id));
    }

    // ---- Admin-only write endpoints ----

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<CellResponse>> getAllForAdmin() {
        return ResponseEntity.ok(cellService.getAllForAdmin());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CellResponse> create(@Valid @RequestBody CellRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cellService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CellResponse> update(
            @PathVariable Long id, @Valid @RequestBody CellRequest request) {
        return ResponseEntity.ok(cellService.update(id, request));
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CellResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(cellService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cellService.delete(id);
        return ResponseEntity.noContent().build();
    }
}