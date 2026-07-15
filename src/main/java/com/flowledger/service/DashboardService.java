package com.flowledger.service;

import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.ExpenseCategorySummaryResponse;
import com.flowledger.dto.response.MonthlySummaryResponse;

import java.util.List;

public interface DashboardService {

    DashboardSummaryResponse getDashboardSummary();

    List<MonthlySummaryResponse> getMonthlySummary();

    List<ExpenseCategorySummaryResponse> getExpenseByCategory();

}