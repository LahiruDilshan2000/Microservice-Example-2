package com.example.microservice.fraud.controller;

import com.example.microservice.fraud.service.FraudCheckHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-check")
@RequiredArgsConstructor
public class FraudCheckHistoryController {

    private final FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping(path = {"{customerId}", "{email}"})
    public ResponseEntity<?> isFraudster(@PathVariable("customerId") Integer customerId,
                                         @PathVariable("email") String email){
        return fraudCheckHistoryService.isFraudulentCustomer(customerId, email);
    }
}
