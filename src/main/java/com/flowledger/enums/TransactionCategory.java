package com.flowledger.enums;

import lombok.Getter;

@Getter
public enum TransactionCategory {

    // Income Categories
    SALARY(TransactionType.INCOME),
    FREELANCE(TransactionType.INCOME),
    BUSINESS(TransactionType.INCOME),
    INVESTMENT(TransactionType.INCOME),
    GIFT(TransactionType.INCOME),
    OTHER_INCOME(TransactionType.INCOME),

    // Expense Categories
    FOOD(TransactionType.EXPENSE),
    GROCERIES(TransactionType.EXPENSE),
    SHOPPING(TransactionType.EXPENSE),
    FUEL(TransactionType.EXPENSE),
    RENT(TransactionType.EXPENSE),
    UTILITIES(TransactionType.EXPENSE),
    HEALTHCARE(TransactionType.EXPENSE),
    EDUCATION(TransactionType.EXPENSE),
    ENTERTAINMENT(TransactionType.EXPENSE),
    TRAVEL(TransactionType.EXPENSE),
    INSURANCE(TransactionType.EXPENSE),
    OTHER_EXPENSE(TransactionType.EXPENSE);

    private final TransactionType transactionType;

    TransactionCategory(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}