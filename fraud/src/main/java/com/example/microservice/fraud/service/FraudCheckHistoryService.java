package com.example.microservice.fraud.service;

import org.springframework.http.ResponseEntity;

public interface FraudCheckHistoryService {

    public ResponseEntity<?> isFraudulentCustomer(Integer customerId);
}
