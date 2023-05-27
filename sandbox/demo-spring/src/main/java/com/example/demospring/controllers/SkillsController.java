package com.example.demospring.controllers;

import com.example.demospring.entities.Skill;
import com.example.demospring.entities.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {
    private final SkillRepository skillRepository;

    public SkillsController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getSkills() {
        var skills = skillRepository.getSkills();
        
        return ResponseEntity.ok(skills);
    }
}
