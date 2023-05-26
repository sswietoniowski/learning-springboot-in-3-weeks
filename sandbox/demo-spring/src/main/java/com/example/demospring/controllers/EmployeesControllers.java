package com.example.demospring.controllers;

import com.example.demospring.entities.Employee;
import com.example.demospring.entities.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeesControllers {
    private final EmployeeService employeeService;

    public EmployeesControllers(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(produces = "application/json")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public Employee getEmployee(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("{id}/addskill/{skillName}")
    public void addSkill(@PathVariable long id, @PathVariable String skillName) {
        employeeService.addSkill(id, skillName);
    }

    @PutMapping("{id}/payrise/{amount}")
    public void employeePayRise(@PathVariable long id, @PathVariable double amount) {
        employeeService.employeePayRise(id, amount);
    }

    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }
}
