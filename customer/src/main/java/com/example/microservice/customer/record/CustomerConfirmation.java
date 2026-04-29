package com.example.microservice.customer.record;

import java.time.LocalDateTime;

public record CustomerConfirmation(
        Integer customerId,
        String customerEmail,
        String firstName,
        String lastName,
        LocalDateTime date){
}
