package com.example.demospring.services;

import com.example.demospring.entities.Employee;
import com.example.demospring.entities.EmployeeRepository;
import com.example.demospring.entities.Skill;
import com.example.demospring.entities.SkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.getEmployees();
    }

    public Optional<Employee> getEmployee(long id) {
        var employee = employeeRepository.getEmployee(id);
        if (employee == null) {
            return Optional.empty();
        }
        return Optional.of(employee);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.insertEmployee(employee);
    }

    @Transactional
    public void addSkill(long employeeId, String skillName) {
        Employee employee = employeeRepository.getEmployee(employeeId);
        Skill skill = skillRepository.getSkill(skillName);
        employeeRepository.addSkill(employee.getEmployeeId(), skill.getSkillId());
    }

    @Transactional
    public void deleteEmployee(long id) {
        var skills = employeeRepository.getEmployee(id).getSkills();
        for (Skill skill : skills) {
            skillRepository.removeSkillForEmployee(id, skill.getSkillId());
        }
        employeeRepository.deleteEmployee(id);
    }

    public void employeePayRise(long id, double amount) {
        employeeRepository.employeePayRise(id, amount);
    }
}
