package com.example.microservice.customer.kafka;

import com.example.microservice.customer.record.CustomerConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerProducer {

    private final KafkaTemplate<String, CustomerConfirmation> kafkaTemplate;

    public void checkFraudConfirmation(CustomerConfirmation customerConfirmation){
        log.info("Sending customer register confirmation.");
        Message<CustomerConfirmation> message = MessageBuilder
                .withPayload(customerConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "customer-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
