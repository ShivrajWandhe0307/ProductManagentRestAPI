# Use official OpenJDK 17 image as base
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Maven/Gradle built JAR into the container
COPY target/ProductManagementRestAPI.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]