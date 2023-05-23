package com.example.demospring.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class BankRepositoryMySQL implements BankRepository {
    private final String connectionString;

    public BankRepositoryMySQL(@Value("${mysql.connectionString}") String connectionString) {
        this.connectionString = connectionString;
        System.out.println("BankRepositoryMySQL constructor");
        System.out.println("MySQL connection string: " + connectionString);
    }

    @Override
    public void update(int amount) {
        System.out.println("MySQL connection string: " + connectionString);
        System.out.println("MySQL: " + amount);
    }
}
