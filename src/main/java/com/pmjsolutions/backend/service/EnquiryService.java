package com.pmjsolutions.backend.service;

import com.pmjsolutions.backend.dto.EnquiryDto;
import com.pmjsolutions.backend.model.Enquiry;
import com.pmjsolutions.backend.repository.EnquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnquiryService {

    private final EnquiryRepository repo;
    private final EmailService emailService;

    public EnquiryService(EnquiryRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    @Transactional
    public Enquiry saveEnquiry(EnquiryDto dto) {
        Enquiry e = new Enquiry();
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        e.setMessage(dto.getMessage());

        Enquiry saved = repo.save(e);

        // send simple confirmation to user
        String subject = "Thanks for contacting PMJ Solutions";
        String body = "Hi " + (saved.getName() == null ? "" : saved.getName()) + ",\n\n"
                + "Thank you for your enquiry. We have received your message and will get back to you soon.\n\n"
                + "— PMJ Solutions";

        emailService.sendConfirmation(saved.getEmail(), subject, body);

        // optional: send internal notification to your team (you can add logic here)

        return saved;
    }
}
