package com.Lider.college_website.controller;

import com.Lider.college_website.dto.request.ContactRequest;
import com.Lider.college_website.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitContactForm(@Valid @RequestBody ContactRequest request) {
        contactService.submitContactForm(request);
        return ResponseEntity.ok("Thank you for contacting us! We'll get back to you soon.");
    }
}