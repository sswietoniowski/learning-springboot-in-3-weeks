package com.example.demospring.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillRepository {
    @PersistenceContext
    protected EntityManager entityManager;

    public Skill getSkill(long id) {
        return entityManager.find(Skill.class, id);
    }

    public Skill getSkill(String name) {
        return entityManager.createQuery("SELECT s FROM Skill s WHERE s.name = :name", Skill.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Skill> getSkills() {
        return entityManager.createQuery("SELECT s FROM Skill s", Skill.class).getResultList();
    }

    public void removeSkillForEmployee(long employeeId, long skillId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        Skill skill = entityManager.find(Skill.class, skillId);
        employee.getSkills().remove(skill);
    }
}
