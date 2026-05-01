# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Production-ready Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Add a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

# High-Performance JVM Flags:
# 1. -XX:+UseZGC: Low latency garbage collection
# 2. -XX:+ZGenerational: Optimization for Java 21+
# 3. -Dspring.threads.virtual.enabled=true: Enable Project Loom
ENTRYPOINT ["java", \
            "-XX:+UseZGC", \
            "-XX:+ZGenerational", \
            "-Dspring.threads.virtual.enabled=true", \
            "-jar", "app.jar"]

EXPOSE 8888