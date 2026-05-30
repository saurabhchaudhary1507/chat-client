# Multi-stage Dockerfile for building and running the chat-client Spring Boot app
# Build stage
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /workspace
# Copy only the necessary files first for better caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
# If mvnw is present, ensure it is executable
RUN if [ -f mvnw ]; then chmod +x mvnw; fi
COPY src ./src
# Build the application (skip tests for faster image builds; run tests in CI)
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy the built jar from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar
# Expose standard Spring Boot port
EXPOSE 8080
# Allow optional JVM options via JAVA_OPTS environment var
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

