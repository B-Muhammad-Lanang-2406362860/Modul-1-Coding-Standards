# Build Stage
FROM gradle:8.7-jdk21-alpine AS builder

WORKDIR /app

# Copy Gradle files first (better caching)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew .

RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build Spring Boot jar
RUN ./gradlew bootJar --no-daemon


# Runtime Stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]