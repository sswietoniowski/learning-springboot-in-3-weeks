package com.example.demospring.entities;

import jakarta.persistence.*; // javax.persistence.* in Spring Boot 2.x.x
import lombok.*;

@Entity
@Table(name = "EMPLOYEES")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPLOYEE_ID")
//    @Getter
//    @Setter
    private long employeeId;

//    @Getter
    private String name;
//    @Getter
    private String region;

    @Column(name = "SALARY")
//    @Getter
    private double dosh;

    @Transient
    private int changeCount;
}
