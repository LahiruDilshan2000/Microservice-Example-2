package com.example.microservice.customer.controller;

import com.example.microservice.customer.record.CustomerRequest;
import com.example.microservice.customer.service.CustomerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customer){
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/{id}")
    @RateLimiter(name = "customerService", fallbackMethod = "fallbackMethod")
    public ResponseEntity<?> getCustomer(@PathVariable String id){
        return customerService.getCustomer(id);
    }

    public ResponseEntity<?> fallbackMethod(String id, Throwable throwable){
        System.out.println("Call fallback method");
        return  new ResponseEntity<>(
                "Call fallback method",
                HttpStatus.OK
        );
    }
}
