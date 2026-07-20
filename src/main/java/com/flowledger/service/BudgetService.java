package com.flowledger.service;

import com.flowledger.dto.request.CreateBudgetRequest;
import com.flowledger.dto.request.UpdateBudgetRequest;
import com.flowledger.dto.response.BudgetProgressResponse;
import com.flowledger.dto.response.BudgetResponse;
import com.flowledger.dto.response.BudgetSuggestionResponse;
import com.flowledger.dto.response.BudgetForecastResponse;

import java.util.List;

public interface BudgetService {

    BudgetResponse createBudget(CreateBudgetRequest request);

    List<BudgetResponse> getAllBudgets();

    BudgetResponse getBudgetById(Long budgetId);

    BudgetResponse updateBudget(Long budgetId,
                                UpdateBudgetRequest request);

    void deleteBudget(Long budgetId);

    BudgetProgressResponse getBudgetProgress(Long budgetId);

    BudgetSuggestionResponse getBudgetSuggestions(Long budgetId);

    BudgetForecastResponse getBudgetForecast(Long budgetId);

}