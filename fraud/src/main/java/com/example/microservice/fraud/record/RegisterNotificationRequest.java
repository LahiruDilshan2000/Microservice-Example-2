package com.example.microservice.fraud.record;

import java.time.LocalDateTime;

public record RegisterNotificationRequest (
        Integer customerId,
        Boolean isFraud,
        LocalDateTime dateTime
){
}
