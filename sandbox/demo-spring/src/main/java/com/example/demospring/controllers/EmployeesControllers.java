package com.example.demospring.controllers;

import com.example.demospring.dtos.EmployeeDto;
import com.example.demospring.entities.Employee;
import com.example.demospring.entities.EmployeeService;
import com.example.demospring.entities.Skill;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeesControllers {
    private final EmployeeService employeeService;

    public EmployeesControllers(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(produces = "application/json")
    public List<Employee> getEmployees(@RequestParam(name="quantity", defaultValue="2", required = false) Optional<Integer> quantity) {
        return employeeService.getEmployees().stream().limit(quantity.orElse(3)).toList(); // limiting here is not a good idea, but it's just for demo
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public EmployeeDto getEmployee(@PathVariable long id) {
        var employee = employeeService.getEmployee(id);
        var skillsAsString = employee.getSkills().stream().map(Skill::getName).reduce("", (acc, skill) -> acc + skill + ", ");
        return new EmployeeDto(employee.getEmployeeId(), employee.getName(), employee.getRegion(), employee.getDosh(), skillsAsString);
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return new EmployeeDto(employee.getEmployeeId(), employee.getName(), employee.getRegion(), employee.getDosh(), "");
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
