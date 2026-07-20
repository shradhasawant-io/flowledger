package com.flowledger.dto.response;

import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSuggestionResponse {

    private Long budgetId;

    private TransactionCategory category;

    private BigDecimal budgetAmount;

    private BigDecimal spentAmount;

    private BigDecimal remainingAmount;

    private BigDecimal usagePercentage;

    private List<BudgetSuggestion> suggestions;
}
