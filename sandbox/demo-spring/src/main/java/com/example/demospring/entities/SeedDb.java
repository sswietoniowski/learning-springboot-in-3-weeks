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
        var sql = "INSERT INTO EMPLOYEES (NAME, REGION, SALARY) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, "John", "London", 100);
        jdbcTemplate.update(sql, "Mary", "New York", 200);
        jdbcTemplate.update(sql, "Bob", "London", 300);
        jdbcTemplate.update(sql, "Susan", "New York", 400);
    }
}
