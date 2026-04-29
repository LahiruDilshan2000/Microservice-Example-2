package com.example.microservice.customer.record;


public record CustomerConfirmation(
        Integer customerId,
        String customerEmail,
        String firstName,
        String lastName,
        String date){
}
