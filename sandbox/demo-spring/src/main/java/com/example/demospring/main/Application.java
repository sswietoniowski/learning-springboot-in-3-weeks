package com.example.demospring.main;

import com.example.demospring.components.BankService;
import com.example.demospring.entities.Employee;
import com.example.demospring.other.MyBean;
import jakarta.persistence.EntityManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(scanBasePackages = "com.example.demospring")
/* Above is equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.demospring")
 */
public class Application {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Application.class, args);

        //usingBeans(ctx);

        seedDatabase(ctx);
        usingJpa(ctx);
    }

    private static void seedDatabase(ConfigurableApplicationContext ctx) {
        // seed database
        var jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        var sql = "INSERT INTO EMPLOYEES (NAME, REGION, SALARY) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, "John", "London", 100);
        jdbcTemplate.update(sql, "Mary", "New York", 200);
        jdbcTemplate.update(sql, "Bob", "London", 300);
        jdbcTemplate.update(sql, "Susan", "New York", 400);

        // seed database using JPA
        var em = ctx.getBean(EntityManager.class);
        var employee = new Employee();
        employee.setName("Boris");
        employee.setRegion("Moscow");
        employee.setDosh(-100);
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
    }

    private static void usingJpa(ConfigurableApplicationContext ctx) {
        // create entity manager
        var em = ctx.getBean(EntityManager.class);

        // create entity
        var employee = new Employee();
        employee.setName("John");
        employee.setRegion("London");
        employee.setDosh(100);

        // persist entity
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // find entity
        var employee2 = em.find(Employee.class, 1L);
        System.out.println("Employee: " + employee2);

        // using JdbcTemplate
        var jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        var sql = "SELECT * FROM EMPLOYEES";
        var employees = jdbcTemplate.query(sql, (rs, rowNum) -> {
            var emp = new Employee();
            emp.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
            emp.setName(rs.getString("NAME"));
            emp.setRegion(rs.getString("REGION"));
            emp.setDosh(rs.getDouble("SALARY"));
            return emp;
        });
        System.out.println("Employees: " + employees);
    }

    private static void usingBeans(ConfigurableApplicationContext ctx) {
        var bankService = ctx.getBean(BankService.class);

        bankService.update(100);

        var applicationArguments = ctx.getBean(ApplicationArguments.class);

        System.out.println("ApplicationArguments: ");
        for (var name : applicationArguments.getNonOptionArgs()) {
            System.out.println("NonOptionArg: " + name);
        }
        for (var optionName : applicationArguments.getOptionNames()) {
            System.out.println("Option: " + optionName + " -> " + applicationArguments.getOptionValues(optionName));
        }

        var myBean = ctx.getBean("myBean", MyBean.class);
        System.out.println("MyBean: " + myBean);
        System.out.println("MyBean.x: " + myBean.getX());

        var myBean2 = ctx.getBean("myBean2", MyBean.class);
        System.out.println("MyBean2: " + myBean2);
        System.out.println("MyBean2.x: " + myBean2.getX());

        var host = ctx.getBean("host", String.class);
        System.out.println("Host: " + host);
    }
}
