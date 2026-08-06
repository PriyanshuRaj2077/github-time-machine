# ⚡ GitHub Time Machine

> **Travel Through Your Code History.**
> A cinematic web experience and high-performance backend platform where developers replay, analyze, and relive their GitHub journey across commit history.

---

## 🌟 Overview

**GitHub Time Machine** is an enterprise-grade web application built to transform raw commit data into an interactive visual documentary experience. Unlike generic analytics dashboards, it tells a developer's story—tracking their growth milestones, dominant technology stacks, commit velocity, and architectural evolution over time.

---

## 🛠️ Technology Stack

### Frontend
- **Core**: HTML5, CSS3, Vanilla JavaScript (ES6 Modules)
- **Design System**: Developer-first dark mode (`#0D1117`, `#161B22`, `#30363D`, `#2EA043`), zero border-radius (`0px`), custom scrollbars.
- **Typography**: JetBrains Mono (Headings/Terminal) + Inter (Body).
- **Zero Third-Party Frameworks**: Built using 100% native web APIs for maximum performance.

### Backend
- **Core Framework**: Java 21 (Amazon Corretto), Spring Boot 3.2.5
- **Data & Persistence**: Spring Data JPA, PostgreSQL (Neon Compatible)
- **Integrations**: GitHub GraphQL API v4
- **Performance & Resilience**: Spring Cache, Token-Bucket Rate Limiting (120 req/min), MDC Correlation Tracing, Exponential Backoff API Retries, GZip Compression.
- **Monitoring & API Docs**: Spring Boot Actuator, Springdoc OpenAPI 3.0 / Swagger UI.

---

## 📁 Repository Structure

```
GitHub Time Machine/
├── css/
│   └── style.css            # Main Design System stylesheet
├── js/
│   ├── utils.js             # Shared helper utilities module
│   ├── api.js               # Resilient API layer & client cache
│   ├── home.js              # Typewriter intro & terminal prompt module
│   └── script.js            # SPA Router & DocumentFragment controller
├── index.html               # Single Page Application entrypoint
├── backend/
│   ├── Dockerfile           # Multi-stage Java 21 Docker packaging
│   ├── docker-compose.yml   # Multi-container service composition
│   ├── pom.xml              # Maven dependencies & build lifecycle
│   └── src/main/
│       ├── java/com/githubtimemachine/
│       │   ├── analytics/   # Analytics engine (9 isolated services)
│       │   ├── config/      # CORS, Cache, OpenAPI, RateLimiting, MDC filters
│       │   ├── controller/  # REST API Controllers
│       │   ├── dto/         # Request/Response DTO contracts
│       │   ├── entity/      # JPA PostgreSQL Entities
│       │   ├── exception/   # Global Exception Handling
│       │   ├── github/      # GitHub GraphQL Client & Mappers
│       │   ├── mapper/      # Entity-to-DTO Mappers
│       │   ├── repository/  # Spring Data JPA Repositories
│       │   ├── service/     # Core Business Services
│       │   └── util/        # Utility helpers
│       └── resources/
│           ├── application.properties
│           ├── application-dev.properties
│           └── application-prod.properties
├── README.md                # Master Documentation
├── DEPLOYMENT_GUIDE.md      # Docker & Cloud Deployment Guide
├── ARCHITECTURE.md          # Visual Architecture & System Design
├── API_DOCUMENTATION.md     # OpenAPI / REST Endpoints Reference
├── DATABASE_SCHEMA.md       # PostgreSQL ERD & Entity Schemas
└── DEPLOYMENT_CHECKLIST.md  # Production Pre-Flight Checklist
```

---

## 🚀 Quick Start Guide

### Prerequisites
- Docker Engine & Docker Compose
- *or* Java 21 (JDK) & PostgreSQL 16

### Running with Docker Compose (Recommended)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/github-time-machine.git
   cd "github-time-machine/backend"
   ```

2. **Set environment variables (Optional)**:
   ```bash
   export GITHUB_API_TOKEN="your_personal_access_token"
   ```

3. **Launch application stack**:
   ```bash
   docker-compose up --build -d
   ```

4. **Access the application**:
   - **Frontend UI**: `http://localhost:8080` (or host via python `-m http.server 8080`)
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - **Actuator Health**: `http://localhost:8080/actuator/health`

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for details.
