package com.example.demospring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class ClientService {
    private final MyClient myClient;
    @Autowired
    private final CircuitBreakerFactory factory;

    public ClientService(MyClient myClient, CircuitBreakerFactory circuitBreakerFactory) {
        this.myClient = myClient;
        this.factory = circuitBreakerFactory;
    }

    public List<String> getEmployees() {
        URI employeesUri = URI.create("http://localhost:8080/api/employees");
        RestTemplate restTemplate = new RestTemplate();

        var employees = restTemplate.getForObject(employeesUri, List.class);

        // WebClient -- webflow / reactive rest apis
        // Feign -- declarative rest client
        // RestTemplate -- imperative rest client

        return employees;
    }

    public String getEmployeesFromFeign() {
        // add circuit breaker
        CircuitBreaker circuitBreaker = factory.create("circuitbreaker");
        String result = circuitBreaker.run(
                () -> myClient.getEmployees().toString(),
                err -> getFallback());

        return result;
    }
    
    public String getFallback() {
        return "something went wrong";
    }
}
