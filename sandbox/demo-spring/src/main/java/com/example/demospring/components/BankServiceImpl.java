package com.example.demospring.components;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class BankServiceImpl implements BankService {
    private BankRepository bankRepository;

    private final Map<String, BankRepository> repositories;

    @Value("#{systemProperties}")
    private Collection<String> systemProperties;

    @Value("${db.engine.mysql.host}")
    private String host;

    @Autowired
    public BankServiceImpl(/*@Qualifier("bankRepositoryMongoDB")*/ BankRepository bankRepository,
                                                                   Map<String, BankRepository> repositories) {
        this.bankRepository = bankRepository;
        this.repositories = repositories;

        System.out.println("BankServiceImpl repositories: ");
        for (var entry : repositories.entrySet()) {
            System.out.println("Bean: " + entry.getKey() + " -> Object: " + entry.getValue());
        }
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("BankServiceImpl postConstruct");

        System.out.println("BankServiceImpl systemProperties: ");
        for (var property : systemProperties) {
            System.out.println("Property: " + property);
        }

        System.out.println("BankServiceImpl host: " + host);
    }

    @Override
    public void update(int amount) {
        System.out.println("BankServiceImpl: " + amount);
        bankRepository.update(amount);
    }
}
