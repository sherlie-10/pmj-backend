package com.pmjsolutions.backend.repository;

import com.pmjsolutions.backend.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
    // You can add custom queries later if needed
}
