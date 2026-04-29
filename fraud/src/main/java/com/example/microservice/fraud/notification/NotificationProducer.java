package com.example.microservice.fraud.notification;

import com.example.microservice.fraud.record.FraudConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, FraudConfirmation> kafkaTemplate;

    public void sendNotification(FraudConfirmation fraudConfirmation){
        log.info("Sending notification fraudster body <{}>", fraudConfirmation);
        Message<FraudConfirmation> message = MessageBuilder
                .withPayload(fraudConfirmation)
                .setHeader(TOPIC, "fraud-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
