# config-core 🚀

A high-performance, centralized configuration engine built with **Spring Cloud Config Server**, optimized for **Java 21 Virtual Threads (Project Loom)** and **Generational ZGC**.

---

## ✨ Key Features

- **Centralized Management:** Externalize configurations for all microservices in a single Git repository.
- **Java 21 Optimized:** Uses Virtual Threads to handle high concurrency with minimal overhead.
- **Stateless Security:** Secured with Basic Authentication for machine-to-machine communication.
- **Production Ready:** Multi-stage Docker builds with ZGC for ultra-low latency GC pauses.
- **Observability:** Built-in monitoring and health checks via Spring Boot Actuator.

---

## 🏗 Architecture

Config-core acts as the **single source of truth** for your infrastructure.

1. **Storage:** Git repository (production) or local filesystem (development)
2. **Engine:** Spring Cloud Config Server running on Virtual Threads (Project Loom)
3. **Clients:** Microservices (Auth, Vault, AML, etc.) fetch configs via REST on startup

---

## 🚀 Quick Start (Development Mode)

### 1. Create Local Config Directory

```bash
mkdir -p ~/my-configs
echo "app.message: Hello from the High-Performance Engine!" > ~/my-configs/application.yml
```

### 2. Start the Engine
```
./mvnw spring-boot:run \
  -Dspring-boot.run.arguments="--spring.profiles.active=native \
  --spring.cloud.config.server.native.search-locations=file://${HOME}/my-configs"
  ```

### 3. Verify the Engine
```
# Health Check
curl http://localhost:8888/actuator/health

# Fetch Configuration
curl -u admin:admin123 http://localhost:8888/any-service/default
```


## Production Deployment
### Build Docker Image
```
docker build -t config-core .
```

### Run Container

```
docker run -p 8888:8888 \
  -e GIT_CONFIG_REPO_URI=https://github.com/your-org/config-repo \
  -e CONFIG_SERVICE_PASS=your-secure-password \
  config-core
 ```

## JVM Performance Tuning
The engine runs with these optimizations:

```
-XX:+UseZGC              # Low-latency garbage collection
-XX:+ZGenerational      # Generational ZGC (Java 21)
spring.threads.virtual.enabled=true  # Enable virtual threads
```

 ## Testing Suite 
Run tests with:
```bash
./mvnw clean test
```

## License
Distributed under the MIT License