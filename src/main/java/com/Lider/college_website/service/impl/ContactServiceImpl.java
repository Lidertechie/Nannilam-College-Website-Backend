package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.ContactRequest;
import com.Lider.college_website.entity.ContactMessage;
import com.Lider.college_website.repository.ContactMessageRepository;
import com.Lider.college_website.service.ContactService;
import com.Lider.college_website.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactMessageRepository contactMessageRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public void submitContactForm(ContactRequest request) {

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(request.getName());
        contactMessage.setEmail(request.getEmail());
        contactMessage.setMobile(request.getMobile());
        contactMessage.setMessage(request.getMessage());
        contactMessageRepository.save(contactMessage);

        emailService.sendContactNotification(request);
        emailService.sendContactThankYou(request);
    }
}