package com.example.microservice.notification.kafka.fraud;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class FraudConfirmation {
    private Integer customerId;
    private String email;
    private Boolean isFraud;
    private LocalDateTime dateTime;
}
