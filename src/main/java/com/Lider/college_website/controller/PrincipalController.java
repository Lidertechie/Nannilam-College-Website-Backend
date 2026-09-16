package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.PrincipalRequest;
import com.Lider.college_website.dto.response.PrincipalResponse;
import com.Lider.college_website.service.PrincipalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/principals")
@RequiredArgsConstructor
public class PrincipalController {

    private final PrincipalService principalService;


    @GetMapping
    public ResponseEntity<List<PrincipalResponse>> getAllPrincipals() {
        return ResponseEntity.ok(principalService.getAllPrincipals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrincipalResponse> getPrincipalById(@PathVariable Long id) {
        return ResponseEntity.ok(principalService.getPrincipalById(id));
    }

    // Admin only - add/edit/delete
    @PostMapping
    public ResponseEntity<PrincipalResponse> createPrincipal(@Valid @RequestBody PrincipalRequest request) {
        return ResponseEntity.ok(principalService.createPrincipal(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrincipalResponse> updatePrincipal(
            @PathVariable Long id,
            @Valid @RequestBody PrincipalRequest request) {
        return ResponseEntity.ok(principalService.updatePrincipal(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrincipal(@PathVariable Long id) {
        principalService.deletePrincipal(id);
        return ResponseEntity.ok("Principal record deleted successfully");
    }
}