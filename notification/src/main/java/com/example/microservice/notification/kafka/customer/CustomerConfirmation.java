package com.example.microservice.notification.kafka.customer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerConfirmation{
        private Integer customerId;
        private String customerEmail;
        private String firstName;
        private String lastName;
        private String date;
}
