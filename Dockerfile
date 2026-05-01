# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy the maven wrapper and pom first (optimizes caching)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# FIX: Grant execution permission inside the container
RUN chmod +x mvnw

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the high-performance runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from Stage 1
COPY --from=build /app/target/*.jar app.jar

# JVM Optimization: Using Virtual Threads and Generational ZGC
ENV JAVA_OPTS="-XX:+UseZGC -XX:+ZGenerational -Dspring.threads.virtual.enabled=true"

EXPOSE 8888

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
