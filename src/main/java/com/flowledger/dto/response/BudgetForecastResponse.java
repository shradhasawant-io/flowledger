package com.flowledger.dto.response;

import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetForecastResponse {

    private Long budgetId;

    private TransactionCategory category;

    private BudgetPeriod period;

    private BigDecimal budgetAmount;

    private BigDecimal spentAmount;

    private Integer totalBudgetDays;

    private Integer elapsedDays;

    private Integer remainingDays;

    private BigDecimal averageDailySpending;

    private BigDecimal forecastedSpending;

    private BigDecimal expectedRemainingAmount;

    private Boolean budgetLikelyToExceed;
}
