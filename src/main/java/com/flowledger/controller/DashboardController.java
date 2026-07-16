package com.flowledger.controller;

import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.ExpenseCategorySummaryResponse;
import com.flowledger.dto.response.MonthlySummaryResponse;
import com.flowledger.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flowledger.dto.response.dashboard.RecentTransactionResponse;
import java.util.List;
import com.flowledger.dto.response.dashboard.HighestExpenseResponse;
import static org.springframework.http.ResponseEntity.ok;
import com.flowledger.dto.response.dashboard.HighestIncomeResponse;
import com.flowledger.dto.response.dashboard.DashboardStatisticsResponse;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary() {

        DashboardSummaryResponse summary =
                dashboardService.getDashboardSummary();

        ApiResponse<DashboardSummaryResponse> response =
                ApiResponse.<DashboardSummaryResponse>builder()
                        .success(true)
                        .message("Dashboard summary retrieved successfully")
                        .data(summary)
                        .build();

        return ok(response);
    }

    @GetMapping("/monthly-summary")
    public ResponseEntity<ApiResponse<List<MonthlySummaryResponse>>> getMonthlySummary() {

        List<MonthlySummaryResponse> monthlySummary =
                dashboardService.getMonthlySummary();

        ApiResponse<List<MonthlySummaryResponse>> response =
                ApiResponse.<List<MonthlySummaryResponse>>builder()
                        .success(true)
                        .message("Monthly summary retrieved successfully")
                        .data(monthlySummary)
                        .build();

        return ok(response);
    }

    @GetMapping("/expense-by-category")
    public ResponseEntity<ApiResponse<List<ExpenseCategorySummaryResponse>>> getExpenseByCategory() {

        List<ExpenseCategorySummaryResponse> response =
                dashboardService.getExpenseByCategory();

        return ResponseEntity.ok(
                ApiResponse.<List<ExpenseCategorySummaryResponse>>builder()
                        .success(true)
                        .message("Expense by category retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/recent-transactions")
    public ResponseEntity<ApiResponse<List<RecentTransactionResponse>>> getRecentTransactions() {

        List<RecentTransactionResponse> response =
                dashboardService.getRecentTransactions();

        return ResponseEntity.ok(
                ApiResponse.<List<RecentTransactionResponse>>builder()
                        .success(true)
                        .message("Recent transactions retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/highest-expense")
    public ResponseEntity<ApiResponse<HighestExpenseResponse>> getHighestExpense() {

        HighestExpenseResponse response =
                dashboardService.getHighestExpense();

        return ResponseEntity.ok(
                ApiResponse.<HighestExpenseResponse>builder()
                        .success(true)
                        .message("Highest expense retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/highest-income")
    public ResponseEntity<ApiResponse<HighestIncomeResponse>> getHighestIncome() {

        HighestIncomeResponse response =
                dashboardService.getHighestIncome();

        return ResponseEntity.ok(
                ApiResponse.<HighestIncomeResponse>builder()
                        .success(true)
                        .message("Highest income retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<DashboardStatisticsResponse>> getDashboardStatistics() {

        DashboardStatisticsResponse response =
                dashboardService.getDashboardStatistics();

        return ResponseEntity.ok(
                ApiResponse.<DashboardStatisticsResponse>builder()
                        .success(true)
                        .message("Dashboard statistics retrieved successfully")
                        .data(response)
                        .build()
        );
    }
}