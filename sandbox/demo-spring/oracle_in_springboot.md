# How to use Oracle Database in Spring Boot application

Helpful links:

- [Configuring the Oracle JDBC drivers in a SpringBoot project](https://blogs.oracle.com/developers/post/configuring-the-oracle-jdbc-drivers-in-a-springboot-project).

Used elements:

Maven dependencies:

```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc10</artifactId>
    <version>19.18.0.0</version>
</dependency>
<dependency>
<groupId>com.oracle.database.jdbc</groupId>
<artifactId>ucp</artifactId>
<version>23.2.0.0</version>
</dependency>
```

Other resources:

- [Oracle Connection Pooling With Spring](https://www.baeldung.com/spring-oracle-connection-pooling),
- [Spring Boot With H2 Database](https://www.baeldung.com/spring-boot-h2-database),
- [Integrating Spring Boot and Spring JDBC with H2 and Starter JDBC](https://www.springboottutorial.com/spring-boot-and-spring-jdbc-with-h2),
- [One-Stop Guide to Profiles with Spring Boot](https://reflectoring.io/spring-boot-profiles/).

Interesting tools:

- [Liquibase](https://www.liquibase.org/),
- [Flyway](https://flywaydb.org/).
