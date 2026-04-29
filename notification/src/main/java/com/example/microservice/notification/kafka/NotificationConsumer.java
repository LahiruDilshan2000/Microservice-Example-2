package com.example.microservice.notification.kafka;

import com.example.microservice.notification.email.EmailService;
import com.example.microservice.notification.entity.Notification;
import com.example.microservice.notification.enums.NotificationType;
import com.example.microservice.notification.kafka.customer.CustomerConfirmation;
import com.example.microservice.notification.kafka.fraud.FraudConfirmation;
import com.example.microservice.notification.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.microservice.notification.enums.NotificationType.CUSTOMER_CONFIRMATION;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = {"customer-topic"})
    public void consumeCustomerSuccess(CustomerConfirmation customerConfirmation) throws MessagingException {
        log.info(format("Consuming the message from customer-topic:: %s", customerConfirmation));

        notificationRepository.save(
                Notification.builder()
                        .notificationType(CUSTOMER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .customerConfirmation(customerConfirmation)
                        .build()
        );

        var customerNme =  customerConfirmation.getFirstName() + " " + customerConfirmation.getLastName();
        emailService.sendCustomerSuccessEmail(
                customerConfirmation.getCustomerEmail(),
                customerNme,
                customerConfirmation.getDate()
        );
    }

    @KafkaListener(topics = {"fraud-topic"})
    public void consumeFraudSuccess(FraudConfirmation fraudConfirmation) throws MessagingException {
        log.info(format("Consuming the message from fraud-topic:: %s", fraudConfirmation));

        notificationRepository.save(
                Notification.builder()
                        .notificationType(CUSTOMER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .fraudConfirmation(fraudConfirmation)
                        .build()
        );

        ;
        emailService.sendFraudSuccessEmail(
                fraudConfirmation.getEmail(),
                fraudConfirmation.getIsFraud(),
                fraudConfirmation.getDateTime()
        );
    }
}
