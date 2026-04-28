package com.example.microservice.fraud.service.impl;

import com.example.microservice.fraud.entity.FraudCheckHistory;
import com.example.microservice.fraud.record.FraudCheckResponse;
import com.example.microservice.fraud.repository.FraudCheckHistoryRepository;
import com.example.microservice.fraud.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryServiceImpl implements FraudCheckHistoryService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public ResponseEntity<?> isFraudulentCustomer(Integer customerId) {

        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .createdAt(LocalDateTime.now())
                        .isFraudster(false)
                        .build()
        );
        return new ResponseEntity<>(
                new FraudCheckResponse(false),
                HttpStatus.OK
        );
    }
}
