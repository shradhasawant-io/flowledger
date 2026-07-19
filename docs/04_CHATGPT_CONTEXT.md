# FlowLedger - ChatGPT Context

> Purpose:
>
> This file is the primary context document for any new ChatGPT conversation related to FlowLedger.
>
> Before suggesting code, architecture changes, or new features, ChatGPT should first read:
>
> 1. 00_PROJECT_OVERVIEW.md
> 2. 01_PROJECT_STRUCTURE.md
> 3. 02_PROJECT_STATE.md
> 4. 03_GIT_HISTORY.md
> 5. This file

---

# Project Rules

You are helping develop an existing production-style Spring Boot backend called FlowLedger.

This project is already in active development.

Do NOT redesign completed modules.

Continue from the current project state.

---

# Primary Goal

Build a production-quality personal finance management backend using Java and Spring Boot.

The focus is:

- Clean Architecture
- Production-ready code
- Enterprise best practices
- Interview-quality implementation
- Real-world financial analytics

---

# Technology Stack

Java 21

Spring Boot

Spring Security

JWT

Spring Data JPA

Hibernate

MySQL

Redis

Swagger

Maven

---

# Architecture Rules

Always follow:

Controller

↓

Service

↓

Repository

↓

Database

DTO Mapping

Entity

↓

Mapper

↓

DTO

Never violate this architecture.

---

# Coding Standards

Always

- Use DTOs
- Use Builder Pattern
- Use Constructor Injection
- Use BigDecimal for money
- Use LocalDate / LocalDateTime
- Keep Controllers thin
- Put business logic inside Services
- Keep Repositories focused on database access
- Use Swagger annotations
- Validate incoming requests

Never

- Return Entity directly
- Put business logic inside Controller
- Put business logic inside Repository
- Use field injection
- Use double for money

---

# Existing Modules

Completed

- Authentication
- User Management
- Transaction CRUD
- Transaction Filtering
- Dashboard Analytics
- Budget CRUD
- Budget Progress
- Budget Alerts

Only extend these modules.

Do not redesign them unless explicitly requested.

---

# Development Workflow

For every new feature:

1. Design first
2. Explain the business logic
3. Implement step by step
4. Compile
5. Test
6. Commit
7. Tag (when sprint is complete)
8. Update documentation

---

# Response Style

When suggesting code:

- Explain WHY first.
- Then explain the flow.
- Then provide complete compilable code.
- Mention required imports.
- Explain important business decisions.
- Keep architecture consistent.

Do not skip explanations.

---

# Refactoring Policy

Do NOT refactor while a sprint is still in progress.

Only refactor after:

- Sprint completed
- All APIs tested
- Git tag created

Refactoring must never change API behaviour.

---

# Source of Truth

If there is a conflict:

Repository Code
↓

Project Documentation
↓

Conversation History

The repository and documentation always win.

Never assume implementation details.

---

# Before Answering

Always verify:

- Does the project already have this feature?
- Does the repository already contain a similar implementation?
- Does the suggestion match the existing architecture?
- Will this break previous APIs?

If unsure, ask before redesigning.

---

# Current Development

Read PROJECT_STATE.md first.

Continue from the current sprint.

Never restart the project from scratch.

---

# Goal

Help evolve FlowLedger into a production-quality backend while maintaining consistency, readability, and clean architecture.