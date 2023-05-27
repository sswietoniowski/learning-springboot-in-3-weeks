package com.example.demospring.controllers;

import com.example.demospring.dtos.EmployeeDto;
import com.example.demospring.entities.Employee;
import com.example.demospring.entities.Skill;
import com.example.demospring.services.EmployeeService;
import com.example.demospring.services.PromotionPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeesControllers {
    private final EmployeeService employeeService;
    private final PromotionPublisherService promotionPublisherService;

    public EmployeesControllers(EmployeeService employeeService, PromotionPublisherService promotionPublisherService) {
        this.employeeService = employeeService;
        this.promotionPublisherService = promotionPublisherService;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Collection<Employee>> getEmployees(@RequestParam(name = "quantity", defaultValue = "2",
            required
                    = false) Optional<Integer> quantity) {
        var employees = employeeService.getEmployees().stream().limit(quantity.orElse(3)).toList(); // limiting here
        // is not a good idea, but it's just for demo

        return ResponseEntity.ok(employees);
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id) {
        var employee = employeeService.getEmployee(id).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        var skillsAsString = employee.getSkills().stream().map(Skill::getName).reduce("",
                (acc, skill) -> acc + skill + ", ");
        var employeeDto = new EmployeeDto(
                employee.getEmployeeId(), employee.getName(), employee.getRegion(), employee.getDosh(), skillsAsString);

        return ResponseEntity.ok().body(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);

        var employeeDto = new EmployeeDto(employee.getEmployeeId(), employee.getName(), employee.getRegion(),
                employee.getDosh(), "");

        URI uri = URI.create("/api/employees/" + employee.getEmployeeId());
        return ResponseEntity.created(uri).body(employeeDto);
    }

    @PutMapping("{id}/addskill/{skillName}")
    public ResponseEntity<Void> addSkill(@PathVariable long id, @PathVariable String skillName) {
        employeeService.addSkill(id, skillName);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/payrise/{amount}")
    public ResponseEntity<Void> employeePayRise(@PathVariable long id, @PathVariable double amount) {
        employeeService.employeePayRise(id, amount);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/promote")
    public ResponseEntity<Void> promoteEmployee(@PathVariable long id) {
        promotionPublisherService.sendPromotionMessage(id);

        return ResponseEntity.noContent().build();
    }
}
