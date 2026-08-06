# 📡 REST API Documentation

> **GitHub Time Machine — API Reference (OpenAPI 3.0)**
> Interactive OpenAPI documentation is served at `/swagger-ui.html`.

---

## 🌐 Common API Response Format

All REST endpoints return a unified API response envelope `ApiResponse<T>`:

```json
{
  "success": true,
  "message": "Target analyzed successfully",
  "data": { ... },
  "timestamp": "2026-08-06T08:45:00Z"
}
```

Standard Response Headers included on every request:
- `X-Correlation-ID`: Unique request correlation ID for distributed tracing (MDC).
- `Cache-Control`: `public, max-age=300` (5 minutes HTTP client cache).

---

## 📌 Endpoint Specifications

### 1. Analyze Target Profile or Repository
- **HTTP Method**: `POST /api/analyze`
- **Content-Type**: `application/json`
- **Request Body**:
  ```json
  {
    "targetQuery": "torvalds",
    "forceRefresh": false
  }
  ```
- **Response**: `200 OK`
  ```json
  {
    "success": true,
    "message": "Target analyzed successfully",
    "data": {
      "id": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
      "username": "torvalds",
      "name": "Linus Torvalds",
      "avatarUrl": "https://github.com/torvalds.png",
      "bio": "Creator of Linux & Git",
      "publicReposCount": 32,
      "followersCount": 180000,
      "yearsCoding": 18,
      "monthsCoding": 4,
      "daysCoding": 12
    }
  }
  ```

---

### 2. Fetch User Profile
- **HTTP Method**: `GET /api/profile/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK` (`AnalyzedUserResponseDto`)

---

### 3. Fetch User Repositories
- **HTTP Method**: `GET /api/repositories/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK`
  ```json
  {
    "success": true,
    "message": "Repositories retrieved successfully",
    "data": [
      {
        "repoName": "linux",
        "fullName": "torvalds/linux",
        "owner": "torvalds",
        "description": "Linux kernel source tree",
        "primaryLanguage": "C",
        "starsCount": 175000,
        "forksCount": 52000,
        "codebaseAge": "33 Years"
      }
    ]
  }
  ```

---

### 4. Fetch Timeline Milestones
- **HTTP Method**: `GET /api/timeline/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK` (Array of chronological commit milestone objects)

---

### 5. Fetch Replay Documentary Events
- **HTTP Method**: `GET /api/replay/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK` (Array of replay slide objects)

---

### 6. Fetch GitHub Wrapped Summary
- **HTTP Method**: `GET /api/wrapped/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK` (`Map<String, Object>`)

---

### 7. Fetch System Insights & AI Summaries
- **HTTP Method**: `GET /api/insights/{username}`
- **Path Variables**: `username` (string)
- **Response**: `200 OK` (`AnalyticsSnapshotResponseDto`)

---

## ⚠️ HTTP Status Codes & Error Responses

| Status Code | Description | Cause |
| :--- | :--- | :--- |
| `200 OK` | Request succeeded | Target analyzed successfully |
| `400 Bad Request` | Request validation failed | Target query is blank or contains invalid characters |
| `404 Not Found` | Resource not found | GitHub user or repository not found |
| `429 Too Many Requests` | Rate limit exceeded | IP address sent >120 requests/minute |
| `500 Internal Server Error` | Unexpected backend error | Handled by GlobalExceptionHandler |
