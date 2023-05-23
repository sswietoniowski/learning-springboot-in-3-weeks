package com.example.demospring.components;

import org.springframework.stereotype.Repository;

@Repository
public class BankRepositoryMongoDB implements BankRepository {
    public BankRepositoryMongoDB() {
        System.out.println("BookRepositoryMongoDB constructor");
    }

    @Override
    public void update(int amount) {
        System.out.println("MongoDB: " + amount);
    }
}

