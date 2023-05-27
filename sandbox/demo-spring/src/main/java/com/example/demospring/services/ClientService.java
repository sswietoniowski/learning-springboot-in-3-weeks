package com.example.demospring.services;

import com.example.demospring.entities.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class ClientService {
    private final MyClient myClient;

    public ClientService(MyClient myClient) {
        this.myClient = myClient;
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

    public List<Employee> getEmployeesFromFeign() {
        // add circuit breaker
        

        return myClient.getEmployees();
    }
}
