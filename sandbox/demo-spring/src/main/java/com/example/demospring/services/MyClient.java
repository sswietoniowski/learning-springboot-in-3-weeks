package com.example.demospring.services;

import com.example.demospring.entities.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "employee-service", url = "http://localhost:8080/api")
public interface MyClient {

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    List<Employee> getEmployees();
}
