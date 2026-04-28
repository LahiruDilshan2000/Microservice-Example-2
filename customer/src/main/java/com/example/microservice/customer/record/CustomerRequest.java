package com.example.microservice.customer.record;


public record CustomerRequest(
        Integer id,
        String firstName,
        String lastName,
        String email) {
}
