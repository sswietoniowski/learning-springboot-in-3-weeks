package com.example.demospring.entities;

import jakarta.persistence.*; // javax.persistence.* in Spring Boot 2.x.x
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEES")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
//    @Getter
//    @Setter
    private long employeeId = -1;

//    @Getter
    private String name;
//    @Getter
    private String region;

    @Column(name = "SALARY")
//    @Getter
    private double dosh;

    @Transient
    private int changeCount;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "EMPLOYEES_SKILLS",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "SKILL_ID")
    )
    private Set<Skill> skills = Set.of();
}
