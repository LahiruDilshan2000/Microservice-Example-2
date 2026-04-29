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
    private NotificationType notificationType;
    @Embedded
    private CustomerConfirmation customerConfirmation;
    @Embedded
    private FraudConfirmation fraudConfirmation;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
