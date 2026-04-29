package com.example.microservice.customer.service.impl;

import com.example.microservice.customer.Repository.CustomerRepository;
import com.example.microservice.customer.entity.Customer;
import com.example.microservice.customer.exception.CustomException;
import com.example.microservice.customer.record.CustomerRequest;
import com.example.microservice.customer.record.FraudCheckResponse;
import com.example.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> registerCustomer(CustomerRequest request) {

        if(customerRepository.existsByEmail(request.email())){
            throw new CustomException(
                   format("Email already exist:: %s", request.email())
            );
        }

        Customer customer = Customer.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (fraudCheckResponse == null || fraudCheckResponse.isFraudster())
//            throw new CustomException("Fraud::");
            throw new IllegalStateException("fraudster");


        return new ResponseEntity<>(
                "Customer registered successfully",
                HttpStatus.OK
        );
    }
}
