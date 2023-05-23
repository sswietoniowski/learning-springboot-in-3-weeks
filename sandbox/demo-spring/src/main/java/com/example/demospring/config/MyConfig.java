package com.example.demospring.config;

import com.example.demospring.other.MyBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile("development")
public class MyConfig {
    @Bean
    @Scope("prototype")
    public MyBean myBean() {
        var bean = new MyBean();
        bean.setX(100);
        bean.setY("Hello");
        return bean;
    }

    @Bean
    public MyBean myBean2(MyBean myBean) {
        myBean.setX(1000);
        myBean.setY("Hello World");
        return myBean;
    }

    @Bean
    public String host(@Value("${db.engine.mysql.host}") String host) {
        return host;
    }
}
