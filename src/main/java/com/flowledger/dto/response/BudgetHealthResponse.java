package com.flowledger.dto.response;

import com.flowledger.enums.BudgetHealthStatus;
import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetHealthResponse {

    private Long budgetId;

    private TransactionCategory category;

    private BudgetPeriod period;

    private BigDecimal budgetAmount;

    private BigDecimal spentAmount;

    private BigDecimal percentageUsed;

    private Integer healthScore;

    private BudgetHealthStatus healthStatus;

    private Boolean budgetLikelyToExceed;

    private Integer totalSuggestions;

    private String summary;
}
