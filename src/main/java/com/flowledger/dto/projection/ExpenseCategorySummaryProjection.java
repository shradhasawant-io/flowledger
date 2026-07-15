package com.flowledger.dto.projection;

import com.flowledger.enums.TransactionCategory;

import java.math.BigDecimal;

public interface ExpenseCategorySummaryProjection {

    TransactionCategory getCategory();

    BigDecimal getTotalExpense();

}