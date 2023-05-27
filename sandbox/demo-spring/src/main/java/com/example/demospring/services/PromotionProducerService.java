package com.example.demospring.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PromotionProducerService {
    private static final Logger logger = Logger.getLogger(PromotionProducerService.class.getName());
    private static final String PROMOTION_TOPIC_NAME = "promotion";
    private static final String EMPLOYEE_TOPIC_NAME = "employee";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> objectKafkaTemplate;
    private final EmployeeService employeeService;

    public PromotionProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                    KafkaTemplate<String, Object> objectKafkaTemplate,
                                    EmployeeService employeeService) {
        this.kafkaTemplate = kafkaTemplate;
        this.employeeService = employeeService;
        this.objectKafkaTemplate = objectKafkaTemplate;
    }

    public void sendPromotionMessage(long employeeId) {
        logger.info(">>>>>>>>>>> Sending promotion message for employee " + employeeId);
        var key = String.valueOf(employeeId);
        var value = "Employee " + employeeId + " has been promoted!";
        this.kafkaTemplate.send(PROMOTION_TOPIC_NAME, key, value);
    }

    public void sendEmployeeMessage(long employeeId) {
        logger.info(">>>>>>>>>>> Sending employee details for employee " + employeeId);
        var key = String.valueOf(employeeId);
        var employee = employeeService.getEmployee(employeeId).orElseThrow();
        this.objectKafkaTemplate.send(EMPLOYEE_TOPIC_NAME, key, employee);
    }
}
