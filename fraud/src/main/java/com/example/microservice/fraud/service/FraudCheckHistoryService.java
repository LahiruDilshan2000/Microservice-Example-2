package com.example.microservice.fraud.service;

import org.springframework.http.ResponseEntity;

public interface FraudCheckHistoryService {

    ResponseEntity<?> isFraudulentCustomer(Integer customerId, String email);
}
