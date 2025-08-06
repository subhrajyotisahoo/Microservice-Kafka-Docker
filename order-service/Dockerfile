# Use Java 21 JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/order-service-0.0.1-SNAPSHOT.jar orderservice.jar

# Expose port 8083 (Order-Service)
EXPOSE 8083

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "orderservice.jar"]
