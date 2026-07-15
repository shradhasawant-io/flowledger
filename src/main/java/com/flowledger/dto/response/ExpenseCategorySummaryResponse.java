package com.flowledger.dto.response;

import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategorySummaryResponse {

    private TransactionCategory category;

    private BigDecimal totalExpense;

}