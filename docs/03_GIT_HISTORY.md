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

# Upcoming Release

Version

v1.4.0

Planned Features

- Budget Health Score
- Budget Forecast
- Smart Budget Suggestions
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