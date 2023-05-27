package com.example.demospring.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PromotionPublisherService {
    public final String TOPIC_NAME = "promotion";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PromotionPublisherService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPromotionMessage(long employeeId) {

        var key = String.valueOf(employeeId);
        var value = "Employee " + employeeId + " has been promoted!";
        this.kafkaTemplate.send(TOPIC_NAME, key, value);
    }
}
