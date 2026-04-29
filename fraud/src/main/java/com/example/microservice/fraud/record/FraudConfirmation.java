package com.example.microservice.fraud.record;


public record FraudConfirmation(
        Integer customerId,
        String email,
        Boolean isFraud,
        String dateTime
){
}
