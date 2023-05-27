package com.example.demospring.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PromotionProducerService {
    private static final Logger logger = Logger.getLogger(PromotionProducerService.class.getName());
    private static final String TOPIC_NAME = "promotion";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PromotionProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPromotionMessage(long employeeId) {
        logger.info(">>>>>>>>>>> Sending promotion message for employee " + employeeId);
        var key = String.valueOf(employeeId);
        var value = "Employee " + employeeId + " has been promoted!";
        this.kafkaTemplate.send(TOPIC_NAME, key, value);
    }
}
