package com.example.microservice.customer.feign;

import com.example.microservice.customer.record.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "FRAUD",
        url = "${application.config.fraud-url}"
)
public interface FraudClient {

    @GetMapping(path = {"{customerId}"})
    Optional<FraudCheckResponse> isFraudster(@PathVariable("customerId") Integer customerId);
}
