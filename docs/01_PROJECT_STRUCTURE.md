# FlowLedger - Project Structure

> This document describes the actual repository structure, package responsibilities, and dependency rules used in FlowLedger.

---

# Repository Structure

```text
flowledger/
│
├── docs/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── flowledger/
│   │   │
│   │   ├── config/
│   │   ├── constant/
│   │   ├── controller/
│   │   ├── dto/
│   │   │   ├── projection/
│   │   │   ├── request/
│   │   │   └── response/
│   │   │       └── dashboard/
│   │   ├── entity/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── scheduler/
│   │   ├── security/
│   │   ├── service/
│   │   │   └── impl/
│   │   ├── specification/
│   │   ├── util/
│   │   ├── validation/
│   │   └── FlowledgerApplication.java
│   │
│   └── resources/
│       ├── static/
│       ├── templates/
│       ├── application.yml
│       ├── application-dev.yml
│       └── application-prod.yml
│
├── src/test/
│
├── pom.xml
├── HELP.md
└── README.md (Recommended)
```

---

# Package Responsibilities

## config

Contains Spring configuration classes.

Examples

- SecurityConfig
- OpenApiConfig
- DevelopmentDataInitializer
- SampleTransaction

Responsibilities

- Spring configuration
- Bean configuration
- Swagger configuration
- Security configuration
- Development-only initialization

Business logic must NOT be placed here.

---

## constant

Stores application constants.

Examples

- API paths
- Application constants
- Fixed values

Business logic must NOT be placed here.

---

## controller

Handles HTTP requests.

Responsibilities

- Receive request
- Validate request
- Call Service
- Return ResponseEntity

Controllers must NOT contain business logic.

Current Controllers

- AuthController
- UserController
- TransactionController
- DashboardController
- BudgetController

---

## dto

Contains all Data Transfer Objects.

### request/

Incoming request payloads.

Examples

- LoginRequest
- RegisterRequest
- CreateTransactionRequest
- UpdateTransactionRequest
- CreateBudgetRequest
- UpdateBudgetRequest

---

### response/

Outgoing API responses.

Examples

- LoginResponse
- UserResponse
- TransactionResponse
- BudgetResponse
- BudgetProgressResponse

---

### response/dashboard/

Dashboard-specific response models.

Examples

- DashboardSummaryResponse
- DashboardStatisticsResponse
- MonthlySummaryResponse
- HighestIncomeResponse
- HighestExpenseResponse
- RecentTransactionResponse

---

### projection/

Spring Data JPA projections.

Examples

- MonthlySummaryProjection
- ExpenseCategorySummaryProjection

Only repository queries should use projections.

---

## entity

JPA entities.

Current Entities

- User
- Transaction
- Budget
- BaseEntity

Entities represent database tables.

Entities must NEVER be returned directly from controllers.

---

## enums

Application enums.

Examples

- UserRole
- TransactionType
- TransactionCategory
- PaymentMethod
- Currency
- BudgetPeriod
- BudgetStatus
- BudgetAlertType
- BillStatus

---

## exception

Contains

- Custom exceptions
- Global exception handler
- Error response classes

Only exception-related classes belong here.

---

## mapper

Converts

Entity ↔ DTO

Responsibilities

- Entity → Response
- Request → Entity

Business logic should not be implemented inside mappers.

---

## repository

Spring Data JPA repositories.

Responsibilities

- Database access
- JPQL queries
- Native SQL
- Aggregate queries
- Specifications

Repositories must NOT contain business logic.

---

## scheduler

Scheduled jobs.

Examples

- Reminder jobs
- Recurring transactions
- Budget notifications

(Currently reserved for future features.)

---

## security

Authentication & Authorization.

Responsibilities

- JWT
- Authentication Filter
- UserDetails
- Security utilities

---

## service

Contains service interfaces.

Example

- AuthService
- UserService
- TransactionService
- DashboardService
- BudgetService

Interfaces define the public contract.

---

## service.impl

Contains service implementations.

Responsibilities

- Business logic
- Validation
- Calculations
- Analytics
- Transactions

This is where most application logic belongs.

---

## specification

Contains JPA Specifications.

Current

- TransactionSpecification

Purpose

Dynamic filtering.

---

## util

Utility classes.

Only reusable helper utilities belong here.

---

## validation

Custom validation classes.

Examples

- Custom annotations
- Validators

---

# Dependency Rules

The dependency flow must always follow:

```text
Controller
        ↓
Service Interface
        ↓
Service Implementation
        ↓
Repository
        ↓
Database
```

Never reverse this flow.

---

# Layer Responsibilities

| Layer | Responsibility |
|--------|----------------|
| Controller | HTTP handling |
| DTO | API contract |
| Mapper | Entity ↔ DTO conversion |
| Service | Business logic |
| Repository | Database access |
| Entity | Database model |

---

# Package Rules

✅ Controllers call Services.

✅ Services call Repositories.

✅ Repositories access the database.

✅ DTOs are used for API communication.

✅ Entities stay inside the persistence layer.

❌ Controller → Repository

❌ Repository → Controller

❌ Controller → Entity Response

❌ Business Logic inside Controller

❌ Business Logic inside Repository

---

# Current Module Status

Completed

- Authentication
- Transactions
- Dashboard
- Budget CRUD
- Budget Progress
- Budget Alerts

Current Development

Sprint 5.x — Budget Analytics

---

# Notes

This document reflects the current repository structure.

Whenever a new package is introduced, update this file so that it remains the source of truth for the project.