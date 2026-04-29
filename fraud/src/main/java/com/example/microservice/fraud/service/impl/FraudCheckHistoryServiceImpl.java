package com.example.microservice.fraud.service.impl;

import com.example.microservice.fraud.entity.FraudCheckHistory;
import com.example.microservice.fraud.notification.NotificationProducer;
import com.example.microservice.fraud.record.FraudCheckResponse;
import com.example.microservice.fraud.record.FraudConfirmation;
import com.example.microservice.fraud.repository.FraudCheckHistoryRepository;
import com.example.microservice.fraud.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudCheckHistoryServiceImpl implements FraudCheckHistoryService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
    private final NotificationProducer notificationProducer;

    public ResponseEntity<?> isFraudulentCustomer(Integer customerId, String email) {

        FraudCheckHistory fraudCheckHistory = fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .email(email)
                        .createdAt(LocalDateTime.now())
                        .isFraudster(false)
                        .build()
        );

        // send fraud customer notification
        notificationProducer.sendNotification(
                new FraudConfirmation(
                        customerId,
                        email,
                        fraudCheckHistory.getIsFraudster(),
                        fraudCheckHistory.getCreatedAt()

                )
        );

        return new ResponseEntity<>(
                new FraudCheckResponse(fraudCheckHistory.getIsFraudster()),
                HttpStatus.OK
        );
    }
}
