package com.pmjsolutions.backend.controller;

import com.pmjsolutions.backend.dto.EnquiryDto;
import com.pmjsolutions.backend.model.Enquiry;
import com.pmjsolutions.backend.service.EnquiryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enquiry")
public class EnquiryController {

    private final EnquiryService service;

    public EnquiryController(EnquiryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createEnquiry(@Valid @RequestBody EnquiryDto dto) {
        Enquiry saved = service.saveEnquiry(dto);
        return ResponseEntity.ok().body(new ApiResponse(true, "Enquiry received", saved.getId()));
    }

    // health check simple GET
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    static class ApiResponse {
        private boolean success;
        private String message;
        private Long id;

        public ApiResponse(boolean success, String message, Long id) {
            this.success = success;
            this.message = message;
            this.id = id;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Long getId() { return id; }
    }
}

