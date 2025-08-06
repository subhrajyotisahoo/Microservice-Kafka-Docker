# Use a base image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/Eureka-Server-0.0.1-SNAPSHOT.jar app.jar

# Expose Eureka server port (typically 8761)
EXPOSE 8761

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
