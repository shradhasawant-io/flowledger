package com.flowledger.service;

import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.ExpenseCategorySummaryResponse;
import com.flowledger.dto.response.MonthlySummaryResponse;
import com.flowledger.dto.response.dashboard.HighestIncomeResponse;
import com.flowledger.dto.response.dashboard.RecentTransactionResponse;
import java.util.List;
import com.flowledger.dto.response.dashboard.HighestExpenseResponse;
import com.flowledger.dto.response.dashboard.DashboardStatisticsResponse;

public interface DashboardService {

    DashboardSummaryResponse getDashboardSummary();

    List<MonthlySummaryResponse> getMonthlySummary();

    List<ExpenseCategorySummaryResponse> getExpenseByCategory();

    List<RecentTransactionResponse> getRecentTransactions();

    HighestExpenseResponse getHighestExpense();

    HighestIncomeResponse getHighestIncome();

    DashboardStatisticsResponse getDashboardStatistics();

}