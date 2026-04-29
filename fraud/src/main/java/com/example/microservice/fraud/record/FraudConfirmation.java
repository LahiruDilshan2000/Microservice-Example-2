package com.example.microservice.fraud.record;

import java.time.LocalDateTime;

public record FraudConfirmation(
        Integer customerId,
        String email,
        Boolean isFraud,
        LocalDateTime dateTime
){
}
