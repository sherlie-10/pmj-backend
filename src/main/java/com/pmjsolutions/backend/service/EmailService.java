package com.pmjsolutions.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmation(String toEmail, String subject, String body) {
        if (toEmail == null || toEmail.isBlank()) return;

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(toEmail);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("Sent enquiry confirmation to {}", toEmail);
        } catch (MailException ex) {
            log.warn("Failed to send confirmation email to {}: {}", toEmail, ex.getMessage());
            // do not fail the request if email fails
        }
    }
}
