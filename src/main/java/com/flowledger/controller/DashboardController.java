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

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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
}