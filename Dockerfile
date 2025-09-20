# Use a Java 21 JDK base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory in the container
WORKDIR /app

# Copy Maven build files
COPY pom.xml .
COPY src ./src

# Build the project using Maven
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Copy the jar to the working directory
COPY target/*.jar app.jar

# Expose the backend port
EXPOSE 8082

# Command to run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
