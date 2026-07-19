# ADR-002 — Use BigDecimal for Monetary Values

Status: Accepted

---

## Context

FlowLedger stores financial data such as income, expenses, savings, and budgets.

Floating-point types (`float`/`double`) can introduce rounding errors.

---

## Decision

All monetary values use `BigDecimal`.

---

## Alternatives Considered

- double
- float
- long (smallest currency unit)

---

## Why This Decision?

- Accurate financial calculations
- No floating-point precision errors
- Standard Java practice for money

---

## Consequences

Pros

- Accurate calculations
- Reliable analytics

Cons

- Slightly more verbose code