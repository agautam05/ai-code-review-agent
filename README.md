# AI Code Review Agent 🚀

An AI-powered code review platform that helps developers improve code quality through intelligent analysis, issue detection, historical learning, and personalized recommendations.

## Problem Statement

Code reviews are often slow, repetitive, and inconsistent. Traditional linters can detect syntax issues but fail to understand recurring mistakes, coding habits, and team-specific development patterns.

The AI Code Review Agent addresses this challenge by analyzing source code, tracking historical reviews, identifying recurring issues, and providing personalized recommendations that help developers continuously improve.

---

## Key Features

### AI-Powered Code Analysis
- Multi-language code review
- Code quality scoring
- AI-generated suggestions
- Improved code generation

### Security & Performance Analysis
- Security vulnerability detection
- Performance optimization recommendations
- Code smell identification
- Best-practice validation

### Learning From Historical Reviews
- Review history tracking
- Recurring issue detection
- Personalized recommendations
- Developer improvement insights

### Analytics Dashboard
- Review statistics
- Issue frequency analysis
- Issue distribution visualization
- Performance tracking

### Additional Features
- JWT Authentication
- Role-Based Access Control
- PDF Report Export
- Admin Dashboard

---

## How It Works

1. User submits source code.
2. Spring Boot API processes the request.
3. Code is analyzed using Groq AI.
4. AI generates:
   - Code Quality Score
   - Issues
   - Suggestions
   - Improved Code
5. Review data is stored in MongoDB.
6. Historical reviews are analyzed to identify recurring coding mistakes.
7. Personalized recommendations are generated based on previous review patterns.

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- MongoDB
- Groq AI API

### Frontend
- React.js
- Vite
- Axios
- Chart.js

---

## System Architecture

```text
React Frontend
      │
      ▼
Spring Boot REST API
      │
      ▼
    Groq AI
      │
      ▼
   MongoDB
```

---

## Key Innovation

Unlike traditional code review tools, the AI Code Review Agent stores historical reviews, identifies recurring mistakes, and generates personalized recommendations based on previous coding patterns.

This enables continuous developer improvement rather than one-time code analysis.

---

## Screenshots

### AI Code Review
(Add Screenshot)

### Personalized Recommendations
(Add Screenshot)

### Recurring Issue Detection
(Add Screenshot)

### Dashboard Analytics
(Add Screenshot)

### Review History
(Add Screenshot)

---

## Future Improvements

- Team-wide coding standards learning
- GitHub Repository Integration
- Pull Request Reviews
- CI/CD Integration
- Advanced Security Analysis
- Team Analytics Dashboard

---

## Author

**Aman Gautam**

---

## Repositories

Frontend Repository:
https://github.com/agautam05/ai-code-review-agent-frontend

Backend Repository:
https://github.com/agautam05/ai-code-review-agent
