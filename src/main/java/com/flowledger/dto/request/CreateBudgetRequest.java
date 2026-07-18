package com.flowledger.dto.request;

import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.TransactionCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateBudgetRequest {

    @NotNull(message = "Budget amount is required")
    @DecimalMin(value = "0.01", message = "Budget amount must be greater than zero")
    private BigDecimal budgetAmount;

    @NotNull(message = "Category is required")
    private TransactionCategory category;

    @NotNull(message = "Budget period is required")
    private BudgetPeriod period;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

}