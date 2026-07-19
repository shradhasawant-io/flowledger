package com.flowledger.dto.response;

import com.flowledger.enums.BudgetAlertType;
import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.BudgetStatus;
import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetProgressResponse {

    private Long budgetId;

    private TransactionCategory category;

    private BudgetPeriod period;

    private BigDecimal budgetAmount;

    private BigDecimal spentAmount;

    private BigDecimal remainingAmount;

    private BigDecimal percentageUsed;

    private BudgetStatus status;

    private Boolean showAlert;

    private BudgetAlertType alertType;

    private String alertMessage;
}
