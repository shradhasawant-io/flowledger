# ADR-001 — Use Layered Architecture

Status: Accepted

---

## Context

FlowLedger is intended to be a production-quality Spring Boot backend.

The project needs a clear separation between HTTP handling, business logic, and database access.

---

## Decision

Use Layered Architecture:

Controller
↓
Service
↓
Repository
↓
Database

DTOs and Mappers separate the API layer from persistence.

---

## Alternatives Considered

- Controller → Repository directly
- Domain-driven architecture
- Hexagonal architecture

---

## Why This Decision?

- Simple to understand
- Standard Spring Boot approach
- Easy for interview discussions
- Good separation of responsibilities

---

## Consequences

Pros

- Clean code
- Easy testing
- Easy maintenance

Cons

- More classes
- Slightly more boilerplate