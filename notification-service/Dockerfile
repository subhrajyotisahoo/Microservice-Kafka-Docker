# Use Java 21 JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/notification-service-0.0.1-SNAPSHOT.jar notificationservice.jar

# Expose port 8084 (Notification Service)
EXPOSE 8084

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "notificationservice.jar"]
