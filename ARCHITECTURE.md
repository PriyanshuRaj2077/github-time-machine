# 🏗️ System Architecture & Design Documentation

> **GitHub Time Machine — System Topology**
> End-to-end structural documentation covering system components, data flow pipelines, package organization, and security boundaries.

---

## 📐 System Architecture Diagram

```mermaid
graph TD
    Client["Browser / Vanilla JS SPA"]
    RateFilter["RateLimitingFilter (120 req/min)"]
    MdcFilter["MdcLoggingFilter (X-Correlation-ID)"]
    Controller["AnalysisController (REST API)"]
    Service["AnalysisServiceImpl"]
    Mapper["EntityDtoMapper"]
    GitHubClient["GitHubClient (GraphQL + Exponential Backoff)"]
    AnalyticsEngine["AnalyticsProcessor (9 Isolated Services)"]
    CacheManager["Spring Cache Manager"]
    DB[("PostgreSQL Database (Neon)")]
    GitHubAPI["GitHub GraphQL API v4"]

    Client -->|HTTP GET / POST| RateFilter
    RateFilter --> MdcFilter
    MdcFilter --> Controller
    Controller --> Service
    Service -->|Cached Check| CacheManager
    Service -->|Fetch Repos & Profile| GitHubClient
    GitHubClient -->|GraphQL HTTP POST| GitHubAPI
    Service -->|Analyze Data| AnalyticsEngine
    Service -->|Map Entity <-> DTO| Mapper
    Service -->|JPA Persist| DB
```

---

## 🔄 Sequence Diagram: User Analysis Flow

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant SPA as Vanilla JS SPA (script.js)
    participant API as ApiService (api.js)
    participant Backend as AnalysisController
    participant GitHub as GitHubClient
    participant DB as PostgreSQL DB

    User->>SPA: Enters username or repo URL
    SPA->>API: analyze(query)
    API->>API: Check 5-min client cache
    alt Cache Miss
        API->>Backend: GET /api/analyze/{query}
        Backend->>GitHub: Fetch user profile & repo GraphQL
        GitHub-->>Backend: Return User & Repository DTOs
        Backend->>DB: Persist AnalyzedUser & Snapshots
        DB-->>Backend: Entity Saved
        Backend-->>API: 200 OK + AnalyzedUserResponseDto
        API->>API: Store in memory cache
    else Cache Hit
        API-->>SPA: Return cached response immediately
    end
    SPA->>User: Render Documentary Stage & Dashboard
```

---

## 📦 Package Architecture (Clean Architecture Principles)

```
com.githubtimemachine
├── analytics/           # Dedicated Analytics Engine (Isolated calculation services)
│   ├── CommitStreakAnalyzerService.java
│   ├── DeveloperStatisticsService.java
│   ├── GrowthAnalyzerService.java
│   ├── InactivePeriodDetectionService.java
│   ├── LanguageAnalyzerService.java
│   ├── MilestoneGeneratorService.java
│   ├── RepositoryAnalyzerService.java
│   ├── TimelineGeneratorService.java
│   └── AnalyticsEngineFacadeService.java
├── config/              # Infrastructure Configuration & Filters
│   ├── CacheConfig.java
│   ├── CorsConfig.java
│   ├── MdcLoggingFilter.java
│   ├── OpenApiConfig.java
│   └── RateLimitingFilter.java
├── controller/          # Presentation Layer (REST Endpoints)
│   ├── AiController.java
│   ├── AnalysisController.java
│   └── HealthController.java
├── dto/                 # Data Transfer Objects (Strict boundaries)
│   ├── request/
│   └── response/
├── entity/              # JPA Domain Entities
│   ├── BaseEntity.java
│   ├── AnalyzedUser.java
│   ├── RepositorySnapshot.java
│   └── AnalyticsSnapshot.java
├── exception/           # Centralized Global Exception Handler
├── github/              # GitHub Integration Subsystem
│   ├── client/GitHubClient.java
│   ├── mapper/GitHubMapper.java
│   └── service/GitHubServiceImpl.java
├── mapper/              # Entity to DTO Conversion Component
├── repository/          # Spring Data JPA Persistence Interfaces
└── service/             # Application Core Services & Contracts
```

---

## 🔒 Security Model & Isolation

1. **AI Boundary Isolation**: The AI module accepts **only structured analytics DTOs**. Raw GitHub API payloads are never passed to LLM prompts.
2. **Token Security**: GitHub API tokens are passed via environment variable `GITHUB_API_TOKEN` and never stored in git or exposed to client browsers.
3. **No Field Injection**: All Spring components use strict constructor injection, guaranteeing immutability and testability.
