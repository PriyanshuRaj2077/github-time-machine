# 📋 Production Deployment Pre-Flight Checklist

> **GitHub Time Machine — DevOps Pre-Flight Verification**
> Complete every verification item prior to promoting a build to production staging or live traffic.

---

## 🛡️ Security & Configuration
- [x] **Environment Secrets Verification**: Ensure no database passwords or API tokens are hardcoded in source repositories.
- [x] **GitHub API Token Scope**: Confirm `GITHUB_API_TOKEN` is set in production container environment variables with valid scopes.
- [x] **CORS Configuration**: Restrict `CORS_ALLOWED_ORIGINS` to trusted frontend domains (`https://app.githubtimemachine.com`).
- [x] **Rate Limiting Verification**: Verify `RateLimitingFilter` is active (120 req/min limit per IP).

## ⚡ Performance & Caching
- [x] **Database Indexing**: Confirm database indexes exist on `username`, `created_at`, `analyzed_user_id`, and `primary_language`.
- [x] **Client-Side Caching**: Verify `js/api.js` 5-minute memory cache operates properly.
- [x] **HTTP GZip Compression**: Confirm `Accept-Encoding: gzip` compression header handling in `GitHubClient.java`.
- [x] **DOM Fragment Rendering**: Confirm `document.createDocumentFragment()` single-pass DOM rendering is active.

## 🐳 Containerization & Monitoring
- [x] **Docker Multi-Stage Build**: Validate `Dockerfile` compiles cleanly using `amazoncorretto:21-alpine-jdk`.
- [x] **Docker Compose Multi-Container Orchestration**: Verify `docker-compose up -d` boots backend and PostgreSQL with passing health checks.
- [x] **Spring Boot Actuator Health Probe**: Confirm `/actuator/health` returns `{"status":"UP"}`.
- [x] **OpenAPI Swagger UI**: Confirm `/swagger-ui.html` renders interactive REST documentation.
- [x] **MDC Tracing**: Confirm `X-Correlation-ID` header is attached to all HTTP requests.

---

## 🚀 Sign-Off

- **DevOps Architect**: Approved
- **Lead QA Engineer**: Approved
- **Principal Engineer**: Approved
