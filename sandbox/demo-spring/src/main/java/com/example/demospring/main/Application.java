package com.example.demospring.main;

import com.example.demospring.components.BankService;
import com.example.demospring.other.MyBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
