package com.flowledger.mapper;

import com.flowledger.dto.response.BudgetResponse;
import com.flowledger.entity.Budget;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {

    public BudgetResponse toResponse(Budget budget) {

        if (budget == null) {
            return null;
        }

        return BudgetResponse.builder()
                .id(budget.getId())
                .budgetAmount(budget.getBudgetAmount())
                .category(budget.getCategory())
                .period(budget.getPeriod())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .build();
    }

}