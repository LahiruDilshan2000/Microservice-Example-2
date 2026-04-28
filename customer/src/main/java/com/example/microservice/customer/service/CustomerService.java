package com.example.microservice.customer.service;

import com.example.microservice.customer.record.CustomerRequest;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> registerCustomer(CustomerRequest customer);
}
