# FlowLedger - Git History & Release Notes

> This document contains the complete release history of FlowLedger.
>
> Every Git tag must have a corresponding release note.

---

# Release Strategy

Every stable milestone follows this workflow:

Development

↓

Testing

↓

Git Commit

↓

Git Tag

↓

Update Release Notes

↓

Push to GitHub

---

# Version History

---

# v1.0.0

Release Date

13 July 2026

Git Tag

v1.0.0

Release Type

Major Release

Status

✅ Stable

---

## Features Added

### Authentication

- User Registration
- User Login
- JWT Authentication
- Spring Security Configuration

### Transactions

- Create Transaction
- Update Transaction
- Delete Transaction
- Get Transaction
- Get All Transactions

### User

- User Entity
- User Repository
- User Service

---

## Infrastructure

- Global Exception Handling
- Validation
- Swagger Configuration
- Development Profile
- Production Profile

---

## Database

Tables

- users
- transactions

---

## APIs

Authentication

- POST /api/v1/auth/register
- POST /api/v1/auth/login

Transactions

- CRUD APIs

---

## Breaking Changes

None

---

# v1.1.0

Release Date

13 July 2026

Git Tag

v1.1.0

Release Type

Minor Release

Status

✅ Stable

---

## Features Added

### Dynamic Filtering

- Category Filtering
- Transaction Type Filtering
- Date Filtering
- Amount Filtering

### Pagination

- Pageable Support

### Sorting

- Dynamic Sorting

### Specifications

- JPA Specification

---

## Infrastructure

- Improved Repository Queries

---

## Database

No schema changes.

---

## APIs

Enhanced Transaction Search APIs.

---

## Breaking Changes

None

---

# v1.2.0

Release Date

16 July 2026

Git Tag

v1.2.0

Release Type

Minor Release

Status

✅ Stable

---

## Features Added

Dashboard Module

### Summary

- Total Income
- Total Expense
- Total Savings

### Statistics

- Highest Income
- Highest Expense
- Average Income
- Average Expense
- Best Month
- Worst Month

### Analytics

- Monthly Summary
- Expense Category Summary
- Recent Transactions

---

## Infrastructure

- Aggregate JPQL Queries
- Dashboard DTOs
- Projection Interfaces

---

## Database

No schema changes.

---

## APIs

GET /api/v1/dashboard

GET /api/v1/dashboard/statistics

GET /api/v1/dashboard/monthly-summary

GET /api/v1/dashboard/highest-income

GET /api/v1/dashboard/highest-expense

GET /api/v1/dashboard/recent-transactions

---

## Breaking Changes

None

---

# v1.3.0

Release Date

18 July 2026

Git Tag

v1.3.0

Release Type

Minor Release

Status

✅ Stable

---

## Features Added

### Budget CRUD

- Create Budget
- Update Budget
- Delete Budget
- Get Budget
- Get All Budgets

### Budget Analytics

- Budget Progress
- Budget Status
- Budget Alerts

---

## Infrastructure

- Budget Entity
- Budget Repository
- Budget Mapper
- Budget Service
- Budget Controller

---

## Database

Tables Added

- budgets

Relationships

Budget → User

---

## APIs

POST /api/v1/budgets

GET /api/v1/budgets

GET /api/v1/budgets/{id}

PUT /api/v1/budgets/{id}

DELETE /api/v1/budgets/{id}

GET /api/v1/budgets/{id}/progress

---

## Breaking Changes

None

---
## Sprint 5.3 – Smart Budget Suggestions

Date: 20 July 2026

Completed:
- Added Smart Budget Suggestions endpoint
- Added SuggestionType enum
- Added SuggestionPriority enum
- Implemented BudgetSuggestion DTO
- Implemented BudgetSuggestionResponse DTO
- Added BudgetService#getBudgetSuggestions()
- Added rule-based suggestion engine
- Refactored budget progress calculation into reusable helper
- Eliminated duplicate repository lookup
- Swagger and business testing completed

## Sprint 5.4 – Budget Forecast

Completed:
- Added BudgetForecastResponse
- Added BudgetService#getBudgetForecast()
- Implemented budget forecasting engine
- Reused calculateBudgetProgress()
- Added forecast endpoint
- Swagger testing completed
- Boundary testing completed
- Edge cases verified

## Sprint 5.5 – Budget Health Score

Completed:
- Added BudgetHealthStatus enum
- Added BudgetHealthResponse
- Added getBudgetHealth()
- Implemented penalty-based scoring engine
- Added health endpoint
- Swagger testing completed
- Edge cases verified

📦 FlowLedger v1.3.0 Release
Release Name
FlowLedger v1.3.0
Budget Analytics Module
Version Summary
Version : v1.3.0

Status : Stable Release

Module : Budget Analytics

Release Type : MINOR
Features Included
Budget Management
✅ Budget CRUD

Create Budget

Update Budget

Delete Budget

View Budgets
Budget Analytics
✅ Budget Progress

Budget Usage %

Remaining Amount

Budget Status
Budget Alerts
✅ Budget Alerts

NONE

WARNING

EXCEEDED
Smart Budget Suggestions
✅ Smart Suggestions

Reduce Spending

Maintain Budget

Save Surplus

Priority Based Suggestions
Budget Forecast
✅ Budget Forecast

Average Daily Spending

Forecast Spending

Expected Remaining Amount

Likely To Exceed
Budget Health Score
✅ Budget Health Score

0–100 Score

Health Status

Summary

Integrated Analytics
REST APIs Included
POST   /api/v1/budgets

GET    /api/v1/budgets

GET    /api/v1/budgets/{id}

PUT    /api/v1/budgets/{id}

DELETE /api/v1/budgets/{id}

GET    /api/v1/budgets/{id}/progress

GET    /api/v1/budgets/{id}/suggestions

GET    /api/v1/budgets/{id}/forecast

GET    /api/v1/budgets/{id}/health
Testing Status
CRUD Tests                    ✅

Progress Tests                ✅

Alert Tests                   ✅

Suggestion Tests              ✅

Forecast Tests                ✅

Health Score Tests            ✅

Boundary Tests                ✅

Edge Case Tests               ✅

Swagger Verification          ✅
Known Technical Debt

We should explicitly record this instead of forgetting it.

Technical Debt (Deferred)

• Duplicate service composition in BudgetHealthService
• Repository lookups can be optimized
• Shared analytics helper extraction

Status: Deferred to "Budget Module Refactoring Sprint".
# Upcoming Release

Version

v1.4.0

Planned Features

- Budget Health Score
- Budget Forecast
- Spending Insights

Status

🟡 In Development

---

# Release Checklist

Before creating a Git tag:

- All features completed
- Application compiles successfully
- APIs tested in Postman
- Swagger verified
- Documentation updated
- Git committed
- Git tag created
- GitHub updated

---

# Notes

Every new Git tag must be added to this document immediately after release.

This document is the official release history of FlowLedger.