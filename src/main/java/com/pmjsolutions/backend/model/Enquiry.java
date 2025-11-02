package com.pmjsolutions.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("businessEmail")
    private String businessEmail;

    @JsonProperty("company")
    private String company;

    @JsonProperty("interest")
    private String interest;

    @JsonProperty("location")
    private String location;

    @JsonProperty("message")
    private String message;

    @JsonProperty("communications")
    private boolean communications;
}
