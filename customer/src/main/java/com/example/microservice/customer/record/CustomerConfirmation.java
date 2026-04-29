package com.example.microservice.customer.record;

import java.time.LocalDateTime;

public record CustomerConfirmation(
        String customerEmail,
        String firstName,
        LocalDateTime date){
}
