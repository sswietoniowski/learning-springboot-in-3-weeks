package com.example.demospring.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    @PersistenceContext
    protected EntityManager entityManager;

    public Employee getEmployee(long id) {
        return entityManager.find(Employee.class, id);
    }

    public long getEmployeeCount() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();
    }

    public List<Employee> getEmployees() {
        String jpql = "SELECT e FROM Employee e";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        return query.getResultList();
    }

    @Transactional
    public void insertEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Transactional
    public void addSkill(long employeeId, long skillId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        Skill skill = entityManager.find(Skill.class, skillId);
        employee.getSkills().add(skill);
    }

    @Transactional
    public void employeePayRise(long id, double amount) {
        Employee employee = entityManager.find(Employee.class, id);
        employee.setDosh(employee.getDosh() + amount);
    }

    @Transactional
    public void deleteEmployee(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }
}
