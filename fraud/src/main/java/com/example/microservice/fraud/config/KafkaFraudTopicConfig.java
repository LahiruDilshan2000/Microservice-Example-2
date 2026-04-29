package com.example.microservice.fraud.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaFraudTopicConfig {

    @Bean
    public NewTopic fraudTopic() {
        return TopicBuilder
                .name("fraud-topic")
                .build();
    }
}
