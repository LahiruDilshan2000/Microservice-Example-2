package com.example.microservice.customer.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("FRAUD")
public interface FraudClient {
}
