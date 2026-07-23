package com.flowledger.dto.response;

import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpendingInsightsResponse {

    private BigDecimal totalExpense;

    private BigDecimal averageDailyExpense;

    private TransactionCategory highestSpendingCategory;

    private BigDecimal highestCategoryAmount;

    private TransactionCategory lowestSpendingCategory;

    private BigDecimal lowestCategoryAmount;

    private BigDecimal highestCategoryPercentage;

    private Integer totalExpenseTransactions;

    private String insightMessage;
}
