# AI Code Review Agent

## Problem Statement

Code review is often slow, repetitive, and inconsistent. Traditional linters can detect syntax issues but fail to understand team-specific coding standards, recurring mistakes, and architectural preferences.

Our AI Code Review Agent helps developers improve code quality by analyzing source code, tracking review history, identifying recurring issues, and generating personalized recommendations over time.

## Features

- AI-powered code review
- Multi-language code analysis
- Code quality scoring
- Security vulnerability detection
- Performance optimization suggestions
- AI-generated improved code
- Review history tracking
- Recurring issue detection
- Personalized recommendations
- PDF report generation
- JWT-based authentication
- Admin dashboard and analytics

## How It Works

1. User submits source code.
2. The backend sends the code to the AI model.
3. The AI analyzes the code and generates:
   - Quality score
   - Issues
   - Suggestions
   - Improved code
4. Reviews are stored in MongoDB.
5. Previous reviews are analyzed to identify recurring mistakes.
6. Personalized recommendations are generated based on review history.

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

## Key Innovation

Unlike traditional code review tools, the system stores review history and tracks recurring coding issues. This enables personalized recommendations and helps developers continuously improve based on past mistakes.

## Architecture

Frontend (React)
↓
Spring Boot REST API
↓
Groq AI
↓
MongoDB

## Future Improvements

- Team-wide coding standards learning
- GitHub repository integration
- Pull Request reviews
- CI/CD integration
- Advanced security analysis
- Team analytics dashboard

## Screenshots

(Add screenshots here)

## Repositories

Backend:
https://github.com/agautam05/ai-code-review-agent

Frontend:
https://github.com/agautam05/ai-code-review-agent-frontend

## Author

Aman Gautam
