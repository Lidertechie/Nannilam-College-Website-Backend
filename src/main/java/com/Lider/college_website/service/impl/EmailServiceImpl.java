package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.ContactRequest;
import com.Lider.college_website.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.contact.my-email}")
    private String myEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String toEmail, String userName, String resetLink) {
        try {
            Context context = new Context();
            context.setVariable("userName", userName);
            context.setVariable("resetLink", resetLink);
            context.setVariable("expiryMinutes", 30);

            String htmlContent = templateEngine.process("email/reset-password", context);
            sendHtmlEmail(toEmail, "Lider ERP - Password Reset Request", htmlContent);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email");
        }
    }

    @Override
    public void sendContactNotification(ContactRequest request) {
        try {
            Context context = new Context();
            context.setVariable("name", request.getName());
            context.setVariable("email", request.getEmail());
            context.setVariable("mobile", request.getMobile());
            context.setVariable("message", request.getMessage());

            String htmlContent = templateEngine.process("email/contact-notification", context);
            sendHtmlEmail(myEmail, "New Contact Us Message - " + request.getName(), htmlContent);
            log.info("Contact notification sent for: {}", request.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send contact notification", e);
            throw new RuntimeException("Failed to send email");
        }
    }

    @Override
    public void sendContactThankYou(ContactRequest request) {
        try {
            Context context = new Context();
            context.setVariable("name", request.getName());
            context.setVariable("email", request.getEmail());
            context.setVariable("mobile", request.getMobile());
            context.setVariable("message", request.getMessage());

            String htmlContent = templateEngine.process("email/contact-thank-you", context);

            sendHtmlEmailWithReplyTo(
                    request.getEmail(),
                    "Thank You for Contacting Government Arts And Science College",
                    htmlContent,
                    "info@gascnannilam.ac.in");
            log.info("Thank-you email sent to {}", request.getEmail());

        } catch (MessagingException e) {
            log.error("Failed to send thank-you email to: {}", request.getEmail(), e);
            throw new RuntimeException("Failed to send thank-you email");
        }
    }


    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    private void sendHtmlEmailWithReplyTo(String to, String subject, String htmlContent, String replyTo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setReplyTo(replyTo);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}