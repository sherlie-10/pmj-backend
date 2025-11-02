package com.pmjsolutions.backend.controller;

import com.pmjsolutions.backend.model.Enquiry;
import com.pmjsolutions.backend.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // Allow requests from your React frontend
@RequestMapping("/api/enquiries")
public class EnquiryController {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @PostMapping
    public Enquiry submitEnquiry(@RequestBody Enquiry enquiry) {
        // 🔍 Debug print to verify received data
        System.out.println("📥 Received Enquiry: " + enquiry);
        return enquiryRepository.save(enquiry);
    }
}
