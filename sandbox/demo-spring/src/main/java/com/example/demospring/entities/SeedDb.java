package com.example.demospring.entities;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeedDb {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SeedDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        // Add skills
        var sql = "INSERT INTO SKILLS (NAME) VALUES (?)";
        jdbcTemplate.update(sql, "Java");
        jdbcTemplate.update(sql, "Spring");
        jdbcTemplate.update(sql, "Hibernate");
        jdbcTemplate.update(sql, "JPA");

        // Add employees
        sql = "INSERT INTO EMPLOYEES (NAME, REGION, SALARY) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, "John", "London", 100);
        jdbcTemplate.update(sql, "Mary", "New York", 200);
        jdbcTemplate.update(sql, "Bob", "London", 300);
        jdbcTemplate.update(sql, "Susan", "New York", 400);

        // Add skills to employees
        sql = "INSERT INTO EMPLOYEES_SKILLS (EMPLOYEE_ID, SKILL_ID) VALUES (?, ?)";
        jdbcTemplate.update(sql, 1, 1);
        jdbcTemplate.update(sql, 1, 2);
        jdbcTemplate.update(sql, 1, 3);
        jdbcTemplate.update(sql, 1, 4);
        jdbcTemplate.update(sql, 2, 1);
        jdbcTemplate.update(sql, 2, 2);
    }
}
