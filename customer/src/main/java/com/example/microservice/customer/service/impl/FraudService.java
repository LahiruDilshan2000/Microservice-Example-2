package com.example.microservice.customer.service.impl;

import com.example.microservice.customer.feign.FraudClient;
import com.example.microservice.customer.record.FraudCheckResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FraudService {

    private final FraudClient fraudClient;

/*    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )*/
    @CircuitBreaker(name = "fraudServiceCircuitBreaker", fallbackMethod = "circuitBreakerFallBack")
    public Optional<FraudCheckResponse> getDetailsFromFraud(Long id){
        System.out.println("Calling our Feign client to get customer details");
        return fraudClient.getDetails(id);
    }

    public Optional<FraudCheckResponse> circuitBreakerFallBack(Long id, Throwable throwable){
        System.out.println("Circuit Breaker FallBack Call");
        return Optional.empty();
    }
}
