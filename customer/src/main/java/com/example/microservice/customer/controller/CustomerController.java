package com.example.microservice.customer.controller;

import com.example.microservice.customer.record.CustomerRequest;
import com.example.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customer){
        System.out.println("ssss");
        return customerService.registerCustomer(customer);
    }
}
