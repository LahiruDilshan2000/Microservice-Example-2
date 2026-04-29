package com.example.microservice.notification.email;

import lombok.Getter;

@Getter
public enum EmailTemplates {
    CUSTOMER_CONFIRMATION("customer-confirmation.html", "Registation successfully processed"),
    FRAUD_CONFIRMATION("fraud-confirmation.html", "Customer fraud check successfully processed");

    private final String template;
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
