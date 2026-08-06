# GitHub Time Machine

GitHub Time Machine is a full-stack web application that transforms a developer's GitHub history into an interactive and cinematic experience. Instead of presenting raw statistics through a traditional analytics dashboard, the application reconstructs a developer's journey, highlighting milestones, repository growth, coding activity, and AI-generated insights.

The platform supports both public GitHub profile analysis and authenticated GitHub users through OAuth, providing personalized analytics, repository intelligence, and historical replay of development progress.

---

## Features

### Public GitHub Analysis

* Analyze any public GitHub profile without authentication.
* View developer profile information.
* Repository overview and statistics.
* Programming language distribution.
* Repository activity analysis.
* Growth timeline generation.
* GitHub Wrapped summary.
* AI-powered developer insights.

### Repository Analysis

* Analyze any public GitHub repository.
* Repository metadata.
* Language statistics.
* Repository health analysis.
* Architecture insights.
* Contribution difficulty estimation.
* AI-generated repository summary.

### GitHub Replay

* Interactive timeline of a developer's journey.
* Significant milestones.
* Repository creation history.
* Development progression.
* Story-driven replay experience.

### AI Features

* Developer Personality Analysis.
* Developer DNA.
* Growth Summary.
* Timeline Documentary.
* Repository Intelligence.
* GitHub Wrapped Report.

### GitHub Authentication

* GitHub OAuth Login.
* Secure user authentication.
* Personalized dashboard.
* Saved analysis history.
* Future-ready support for extended GitHub permissions.

### Analytics

* Timeline Generator.
* Repository Analyzer.
* Growth Analyzer.
* Language Analyzer.
* Activity Analyzer.
* GitHub Wrapped Generator.

---

## Technology Stack

### Frontend

* HTML5
* CSS3
* Vanilla JavaScript (ES6)

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Maven

### Database

* PostgreSQL (Neon)

### External Services

* GitHub REST API / GraphQL API
* GitHub OAuth
* Gemini API (AI Features)

### Deployment

* Frontend: Vercel
* Backend: Render
* Database: Neon PostgreSQL

---

## Project Architecture

```
GitHub Time Machine

Frontend
│
├── HTML
├── CSS
└── Vanilla JavaScript
        │
        ▼
Spring Boot Backend
│
├── Controllers
├── Services
├── GitHub Integration
├── Analytics Engine
├── AI Module
├── Security
└── Database Layer
        │
        ▼
PostgreSQL (Neon)
│
├── Users
├── Analysis History
├── Repository Cache
├── AI Reports
├── Login Sessions
├── API Usage
└── System Metrics
```

---

## Project Structure

```
github-time-machine/

backend/

├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── github/
├── analytics/
├── ai/
├── security/
├── util/
└── resources/

frontend/

├── assets/
├── css/
├── js/
├── pages/
├── components/
└── index.html
```

---

## Core Modules

### GitHub Integration

Responsible for communication with GitHub APIs, profile retrieval, repository information, and authentication.

### Analytics Engine

Processes GitHub data to generate:

* Growth analysis
* Timeline
* Repository metrics
* Language statistics
* Development insights

### AI Engine

Generates:

* Developer Personality
* Growth Summary
* Repository Summary
* Documentary Timeline
* GitHub Wrapped

### Authentication

Implements GitHub OAuth with Spring Security and secure session management.

### Database Layer

Stores authenticated users, analysis history, cached GitHub data, AI reports, and application metrics.

---

## Environment Variables

Create a `.env` file for local development.

```
SPRING_DATASOURCE_URL=

SPRING_DATASOURCE_USERNAME=

SPRING_DATASOURCE_PASSWORD=

GITHUB_CLIENT_ID=

GITHUB_CLIENT_SECRET=

GITHUB_API_TOKEN=

GEMINI_API_KEY=

JWT_SECRET=
```

Do not commit your `.env` file.

Only commit `.env.example`.

---

## Local Development

### Backend

```
cd backend

mvn clean install

mvn spring-boot:run
```

### Frontend

Serve the frontend using any local web server.

Example:

```
Live Server

or

python -m http.server
```

---

## Deployment

### Frontend

Deploy using Vercel.

### Backend

Deploy using Render.

### Database

Use Neon PostgreSQL.

Store all secrets as environment variables on the hosting provider.

---

## Security

* GitHub OAuth Authentication
* Spring Security
* Environment Variable Management
* Secure API Communication
* Role-Based Authorization
* Protected Administrative Endpoints

---

## Future Roadmap

* Developer comparison.
* Repository comparison.
* AI career recommendations.
* Team analytics.
* Export reports.
* PDF generation.
* Public profile sharing.
* Advanced contribution analytics.

---

## Contributing

Contributions are welcome.

If you discover a bug or would like to propose an enhancement, please open an issue before submitting a pull request.

For major changes, please discuss the proposed design first.

---

## License

This project is licensed under the MIT License.

---

## Author

Developed by **Priyanshu Raj**

GitHub: https://github.com/PriyanshuRaj2077
