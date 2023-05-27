package com.example.demospring.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class PromotionConsumerService {
    @KafkaListener(topics = "promotion", groupId = "group1")
    public void group1ConsumerA(@Header(KafkaHeaders.RECEIVED_KEY) String key,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp,
                                String value) {
        System.out.println(">>>>>>>>>>> Group 1 Consumer A");
        System.out.println(">>>>>>>>>>> Key: " + key);
        System.out.println(">>>>>>>>>>> Topic: " + topic);
        System.out.println(">>>>>>>>>>> Timestamp: " + timestamp);
        System.out.println(">>>>>>>>>>> Value: " + value);
    }

    @KafkaListener(topics = "employee", groupId = "group1")
    public void group1ConsumerB(@Header(KafkaHeaders.RECEIVED_KEY) String key,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp,
                                Object value) {
        System.out.println(">>>>>>>>>>> Group 1 Consumer B");
        System.out.println(">>>>>>>>>>> Key: " + key);
        System.out.println(">>>>>>>>>>> Topic: " + topic);
        System.out.println(">>>>>>>>>>> Timestamp: " + timestamp);
        System.out.println(">>>>>>>>>>> Value: " + value);
    }
}
