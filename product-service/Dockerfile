# Use Java 21 JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the generated JAR file into the container
COPY target/product-service-0.0.1-SNAPSHOT.jar productservice.jar

# Expose port 8082 (Product-Service)
EXPOSE 8082

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "productservice.jar"]
