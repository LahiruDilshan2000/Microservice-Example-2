package com.example.microservice.notification.entity;

import com.example.microservice.notification.enums.NotificationType;
import com.example.microservice.notification.kafka.customer.CustomerConfirmation;
import com.example.microservice.notification.kafka.fraud.FraudConfirmation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_id_sequence",
            sequenceName = "notification_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_sequence"
    )
    private Integer id;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "customerId", column = @Column(name = "cc_customer_id")),
            @AttributeOverride(name = "customerEmail", column = @Column(name = "cc_email")),
            @AttributeOverride(name = "firstName", column = @Column(name = "cc_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "cc_last_name")),
            @AttributeOverride(name = "date", column = @Column(name = "cc_date"))
    })
    private CustomerConfirmation customerConfirmation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "customerId", column = @Column(name = "fc_customer_id")),
            @AttributeOverride(name = "email", column = @Column(name = "fc_email")),
            @AttributeOverride(name = "isFraud", column = @Column(name = "fc_is_fraud")),
            @AttributeOverride(name = "dateTime", column = @Column(name = "fc_date_time"))
    })
    private FraudConfirmation fraudConfirmation;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
