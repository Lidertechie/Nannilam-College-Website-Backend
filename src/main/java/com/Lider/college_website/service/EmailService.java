package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.ContactRequest;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String userName, String resetLink);

    void sendContactNotification(ContactRequest request);

    void sendContactThankYou(ContactRequest request);
}