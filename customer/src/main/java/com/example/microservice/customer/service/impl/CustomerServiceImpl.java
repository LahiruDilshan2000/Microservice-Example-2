package com.example.microservice.customer.service.impl;

import com.example.microservice.customer.Repository.CustomerRepository;
import com.example.microservice.customer.entity.Customer;
import com.example.microservice.customer.exception.CustomException;
import com.example.microservice.customer.feign.FraudClient;
import com.example.microservice.customer.record.CustomerRequest;
import com.example.microservice.customer.record.FraudCheckResponse;
import com.example.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

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


//        FraudCheckResponse fraudCheckResponse = isCustomerFraudsterByRestTemplate(customer.getId());
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );
        FraudCheckResponse fraudCheckResponse = isCustomerFraudsterByFeign(customer.getId());

        if (fraudCheckResponse.isFraudster())
            throw new CustomException("Customer is fraudster!");

        return new ResponseEntity<>(
                "Customer registered successfully",
                HttpStatus.OK
        );
    }

    private FraudCheckResponse isCustomerFraudsterByFeign(Integer id) {

        return this.fraudClient.isFraudster(id)
                .orElseThrow(() -> new CustomException("An error occurred while checking the customer fraud::"));
    }

    private FraudCheckResponse isCustomerFraudsterByRestTemplate(Integer id) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer your-token");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<FraudCheckResponse> responseType =
                new ParameterizedTypeReference<FraudCheckResponse>() {};

        ResponseEntity<FraudCheckResponse> response = restTemplate.exchange(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                HttpMethod.GET,
                requestEntity,
//                FraudCheckResponse.class,
                responseType,
                id
        );

        if (response.getStatusCode().isError()){
            throw new CustomException("An error occurred while checking the customer fraud:: " + response.getStatusCode());
        }
        return  response.getBody();
    }
}
