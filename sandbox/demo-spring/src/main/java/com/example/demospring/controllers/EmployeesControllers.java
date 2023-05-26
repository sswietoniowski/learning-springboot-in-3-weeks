package com.example.demospring.controllers;

import com.example.demospring.dtos.EmployeeDto;
import com.example.demospring.entities.Employee;
import com.example.demospring.entities.EmployeeService;
import com.example.demospring.entities.Skill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeesControllers {
    private final EmployeeService employeeService;

    public EmployeesControllers(EmployeeService employeeService) {
        this.employeeService = employeeService;
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

        try {
            return ResponseEntity.created(new URI("/api/employees/" + employee.getEmployeeId())).body(employeeDto);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
}
