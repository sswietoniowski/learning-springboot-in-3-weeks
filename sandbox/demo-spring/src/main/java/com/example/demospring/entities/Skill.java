package com.example.demospring.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "SKILLS")
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId = -1;
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<Employee> employees = Set.of();
}
