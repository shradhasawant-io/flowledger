package com.flowledger.dto.response;

import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.TransactionCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BudgetResponse {

    private Long id;

    private BigDecimal budgetAmount;

    private TransactionCategory category;

    private BudgetPeriod period;

    private LocalDate startDate;

    private LocalDate endDate;

}