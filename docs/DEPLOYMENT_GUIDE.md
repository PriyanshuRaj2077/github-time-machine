# 🚢 Production Deployment Guide

> **GitHub Time Machine — DevOps Runbook**
> Instructions for packaging, configuring, and deploying the application to production cloud environments (Neon PostgreSQL, AWS ECS, Railway, Render, Docker).

---

## 🛠️ Prerequisites

- **Docker**: Engine version 24.0+
- **Database**: PostgreSQL 16+ (or Neon Serverless Postgres)
- **Runtime**: OpenJDK / Amazon Corretto 21
- **GitHub Token**: Personal Access Token (PAT) with `read:user` and `repo` scope.

---

## 🔑 Environment Variables Reference

Never hardcode credentials or secrets in application source files. Set the following environment variables in your production container orchestrator:

| Variable Name | Required | Default Value | Description |
| :--- | :--- | :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | **Yes** | `prod` | Active Spring profile (`dev` or `prod`). |
| `SPRING_DATASOURCE_URL` | **Yes** | `jdbc:postgresql://postgres:5432/github_time_machine` | PostgreSQL JDBC connection URL. |
| `SPRING_DATASOURCE_USERNAME` | **Yes** | `postgres` | Database username. |
| `SPRING_DATASOURCE_PASSWORD` | **Yes** | `postgrespassword` | Database password. |
| `GITHUB_API_TOKEN` | Optional | `""` | GitHub GraphQL Personal Access Token. |
| `CORS_ALLOWED_ORIGINS` | Optional | `*` | Allowed CORS origins (e.g. `https://yourdomain.com`). |
| `RATE_LIMIT_PER_MINUTE` | Optional | `120` | Maximum HTTP requests per minute per IP address. |

---

## 🐳 Docker Container Packaging

### Multi-Stage Dockerfile Execution
The backend uses a multi-stage Docker build to keep image sizes small (<200MB) and ensure build isolation:

```dockerfile
# Stage 1: Build Phase
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime Phase
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
COPY --from=builder /app/target/github-time-machine-backend-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]
```

### Building & Running Local Image
```bash
cd backend
docker build -t github-time-machine-backend:1.0.0 .
docker run -d -p 8080:8080 --name gtm-backend github-time-machine-backend:1.0.0
```

---

## 🐙 Docker Compose Orchestration

Use `docker-compose.yml` to spin up both PostgreSQL and backend services with health checks:

```bash
docker-compose up -d --build
```

Verify service status:
```bash
docker-compose ps
docker-compose logs -f backend
```

---

## 🔒 Production Hardening Checklist

1. **Disable Public Actuator Endpoints**:
   Restrict Actuator to internal networks or require authentication.
2. **Configure CORS**:
   Set `CORS_ALLOWED_ORIGINS="https://app.githubtimemachine.com"`.
3. **Database SSL**:
   Add `?sslmode=require` to `SPRING_DATASOURCE_URL` when connecting to Neon PostgreSQL.
4. **JVM Memory Limits**:
   Set `-Xms256m -Xmx512m` to prevent container out-of-memory (OOM) kills.
