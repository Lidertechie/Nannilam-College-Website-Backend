package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.ContactRequest;

public interface ContactService {
    void submitContactForm(ContactRequest request);
}