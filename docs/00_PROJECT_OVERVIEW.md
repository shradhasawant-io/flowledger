# FlowLedger

> A modern personal finance management platform built with Java and Spring Boot.

---

# Project Overview

FlowLedger is a backend REST API that helps users manage their personal finances by tracking income, expenses, budgets, savings, and financial insights.

The project is designed to simulate a production-ready fintech backend while following clean architecture and Spring Boot best practices.

---

# Project Goals

- Build a production-quality Spring Boot backend.
- Learn enterprise backend development.
- Demonstrate clean architecture.
- Showcase advanced financial analytics.
- Build an interview-ready portfolio project.

---

# Technology Stack

| Layer | Technology |
|--------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| Database | MySQL |
| ORM | Spring Data JPA (Hibernate) |
| Build Tool | Maven |
| Documentation | Swagger / OpenAPI |
| Cache | Redis |
| Validation | Jakarta Validation |
| Mapping | Manual Mapper Classes |
| Version Control | Git + GitHub |

---

# Architecture

Controller
↓
Service
↓
Repository
↓
Database

DTOs and Mappers are used between Controller and Service.

---

# Core Modules

- Authentication
- User Management
- Transactions
- Dashboard Analytics
- Budget Management
- Budget Analytics
- Savings Goals (Planned)
- Recurring Transactions (Planned)
- Smart Insights (Planned)

---

# Current Project Status

Current Version:
v1.3.0

Completed Modules:

- Authentication
- Transaction CRUD
- Transaction Filtering
- Dashboard Summary
- Dashboard Statistics
- Monthly Dashboard Analytics
- Budget CRUD
- Budget Progress
- Budget Alerts

In Progress:

- Budget Analytics

---

# Development Principles

- Layered Architecture
- DTO-based API design
- Thin Controllers
- Business Logic inside Services
- Repository only accesses database
- Manual Mapping
- BigDecimal for all monetary values
- LocalDate / LocalDateTime for date handling
- JWT Authentication
- RESTful API Design

---

# Repository Structure

src
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── security
├── config
├── exception
└── enums

---

# Version History

| Version | Features |
|----------|----------|
| v1.0.0 | Initial Release |
| v1.1.0 | Transaction Filtering |
| v1.2.0 | Dashboard Analytics |
| v1.3.0 | Budget CRUD |

---

# Next Milestones

- Budget Health Score
- Budget Forecast
- Smart Budget Suggestions
- Spending Insights
- Savings Goals
- Recurring Transactions