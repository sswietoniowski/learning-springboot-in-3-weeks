package com.example.demospring.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class ClientService {
    public List<String> getEmployees() {
        URI employeesUri = URI.create("http://localhost:8080/api/employees");
        RestTemplate restTemplate = new RestTemplate();

        var employees = restTemplate.getForObject(employeesUri, List.class);

        return employees;
    }
}
