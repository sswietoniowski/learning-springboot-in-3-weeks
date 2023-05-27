package com.example.demospring.main;

import com.example.demospring.components.BankService;
import com.example.demospring.entities.Employee;
import com.example.demospring.other.MyBean;
import com.example.demospring.services.ClientService;
import com.example.demospring.services.EmployeeService;
import jakarta.persistence.EntityManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(scanBasePackages = "com.example.demospring")
@EntityScan(basePackages = "com.example.demospring.entities")
@EnableFeignClients(basePackages = "com.example.demospring.services")
//@EnableSwagger2
//@EnableOpenApi
/* Above is equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.demospring")
 */
public class Application {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Application.class, args);

        //usingBeans(ctx);

        //usingJpa(ctx);

        //usingServices(ctx);

        usingRest(ctx);

    }

    private static void usingRest(ConfigurableApplicationContext ctx) {
        var client = ctx.getBean(ClientService.class);
//
//        var employees = client.getEmployees();
//        System.out.println("Employees:");
//        for (var e : employees) {
//            System.out.println(e);
//        }


        var employees2 = client.getEmployeesFromFeign();

        System.out.println("Employees2:");
        for (var e : employees2) {
            System.out.println(e);
        }
    }

    private static void usingServices(ConfigurableApplicationContext ctx) {
        var employeeService = ctx.getBean(EmployeeService.class);

        System.out.println("Employees (initially):");
        for (var e : employeeService.getEmployees()) {
            System.out.println(e);
        }

        var employee = new Employee();
        employee.setName("Jan");
        employee.setRegion("Warsaw");
        employee.setDosh(10000);

        employeeService.addEmployee(employee);

        System.out.println("Employees (after adding a new one):");
        for (var e : employeeService.getEmployees()) {
            System.out.println(e);
        }

        employeeService.employeePayRise(employee.getEmployeeId(), 1000);

        System.out.println("Employees (after pay rise):");
        for (var e : employeeService.getEmployees()) {
            System.out.println(e);
        }

        employeeService.addSkill(employee.getEmployeeId(), "Java");

        System.out.println("Employees (after adding a skill):");
        for (var e : employeeService.getEmployees()) {
            System.out.println(e);
        }

        // TODO: fix this
//        employeeService.deleteEmployee(employee.getEmployeeId());
//
//        System.out.println("Employees (after deleting):");
//        for (var e : employeeService.getEmployees()) {
//            System.out.println(e);
//        }
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

        // update entity
        em.getTransaction().begin();
        employee2.setDosh(200);
        em.getTransaction().commit();

        // delete entity
        em.getTransaction().begin();
        em.remove(employee2);
        em.getTransaction().commit();

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
