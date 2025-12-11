# ------------------------------------------------------
# 1️⃣ BUILD STAGE  (Maven + JDK 21)
# ------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and resolve dependencies first (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests


# ------------------------------------------------------
# 2️⃣ RUNTIME STAGE (JRE 21 — lightweight)
# ------------------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/*.jar app.jar

# Render injects $PORT dynamically — expose for clarity
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
