package com.example.microservice.customer.record;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        Integer id,
        @NotBlank(message = "Customer first name is required!")
        String firstName,
        @NotBlank(message = "Customer last name is required!")
        String lastName,
        @NotBlank(message = "Customer email is required!")
        @Email(message = "Customer email is not a valid email address!")
        String email) {
}
