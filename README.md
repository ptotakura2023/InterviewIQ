# InterviewIQ

**Interview Application Portal** - A comprehensive platform to track job applications, manage interviews, collect feedback, and receive smart recommendations.

## Project Overview

InterviewIQ helps candidates track their entire interview journey from application to offer, while providing interviewers and admins with tools to manage the process efficiently.

### Key Features
- **Application Tracking System** - Track applications across companies with status timeline (Applied → Interviewing → Rejected → Offer)
- **Interview Scheduling** - Schedule and manage interviews with calendar integration
- **Feedback Engine** - Structured feedback with skill scoring (Technical, Behavioral, System Design)
- **Insights Dashboard** - Weak areas, success rate, rejection patterns, and performance trends
- **Smart Recommendations** - Rule-based suggestions triggered by feedback scores
- **Role-Based Access** - Separate interfaces for Candidates, Interviewers, and Admins

## Tech Stack

**Frontend:**
- React 18+ with Vite
- React Router v6
- Axios for API calls
- Tailwind CSS

**Backend:**
- Java 17+
- Spring Boot 3.x
- Spring Security (JWT-based authentication)
- Spring Data JPA + Hibernate
- PostgreSQL

**DevOps:**
- Docker & Docker Compose
- GitHub Actions (CI/CD)
- Git Flow branching strategy

## Project Structure

```
InterviewIQ/
├── backend/           # Spring Boot application
├── frontend/          # React application
├── docs/              # Documentation and project artifacts
├── docker-compose.yml # Local development setup
└── README.md
```

## Development Setup

### Prerequisites
- Java 17 or higher
- Node.js 18+ and npm
- PostgreSQL 14+
- Docker (optional, for containerized development)
- Git

### Quick Start

*Coming soon - setup instructions will be added as we build the project*

## Project Progress

This project is being built incrementally following Agile methodology with the following sprint plan:

- **Sprint 1 (Weeks 1-2):** Project Setup + Authentication & User Management
- **Sprint 2 (Weeks 3-4):** Application Tracking + Interview Scheduling
- **Sprint 3 (Weeks 5-6):** Feedback Engine + Insights Dashboard
- **Sprint 4 (Weeks 7-8):** Smart Recommendations + Admin Panel
- **Sprint 5 (Weeks 9-10):** Testing, Polish & Deployment

## Branching Strategy

We follow GitFlow branching model:

- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - Individual feature branches (e.g., `feature/IIQ-AUTH-01`)
- `hotfix/*` - Emergency production fixes



## Contributors

- Pranay Babu Totakura - Full Stack Developer

---

**Status:** Under Development - Sprint 1 in progress
