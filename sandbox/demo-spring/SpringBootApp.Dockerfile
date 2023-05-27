# Generate image for Spring Boot application
FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=target/*.jar

# Set the working directory
WORKDIR /app

# Copy the jar file
COPY $JAR_FILE app.jar

# Expose the port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]