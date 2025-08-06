# Use Java 21 JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/user-service-0.0.1-SNAPSHOT.jar userservice.jar

# Expose port 8081 (User Service)
EXPOSE 8081

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "userservice.jar"]
