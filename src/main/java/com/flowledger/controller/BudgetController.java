package com.flowledger.controller;

import com.flowledger.dto.request.CreateBudgetRequest;
import com.flowledger.dto.request.UpdateBudgetRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.BudgetProgressResponse;
import com.flowledger.dto.response.BudgetResponse;
import com.flowledger.dto.response.BudgetSuggestionResponse;
import com.flowledger.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.flowledger.dto.response.BudgetForecastResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<ApiResponse<BudgetResponse>> createBudget(
            @Valid @RequestBody CreateBudgetRequest request) {

        BudgetResponse response = budgetService.createBudget(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<BudgetResponse>builder()
                                .success(true)
                                .message("Budget created successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getAllBudgets() {

        List<BudgetResponse> response = budgetService.getAllBudgets();

        return ResponseEntity.ok(
                ApiResponse.<List<BudgetResponse>>builder()
                        .success(true)
                        .message("Budgets retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetResponse>> getBudgetById(
            @PathVariable Long id) {

        BudgetResponse response = budgetService.getBudgetById(id);

        return ResponseEntity.ok(
                ApiResponse.<BudgetResponse>builder()
                        .success(true)
                        .message("Budget retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetResponse>> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBudgetRequest request) {

        BudgetResponse response = budgetService.updateBudget(id, request);

        return ResponseEntity.ok(
                ApiResponse.<BudgetResponse>builder()
                        .success(true)
                        .message("Budget updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBudget(
            @PathVariable Long id) {

        budgetService.deleteBudget(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Budget deleted successfully")
                        .build()
        );
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<ApiResponse<BudgetProgressResponse>> getBudgetProgress(
            @PathVariable Long id) {

        BudgetProgressResponse response =
                budgetService.getBudgetProgress(id);

        return ResponseEntity.ok(
                ApiResponse.<BudgetProgressResponse>builder()
                        .success(true)
                        .message("Budget progress retrieved successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{budgetId}/suggestions")
    @Operation(
            summary = "Get smart budget suggestions",
            description = "Returns personalized smart suggestions for a specific budget."
    )
    public ResponseEntity<ApiResponse<BudgetSuggestionResponse>> getBudgetSuggestions(
            @PathVariable Long budgetId) {

        BudgetSuggestionResponse response =
                budgetService.getBudgetSuggestions(budgetId);


        return ResponseEntity.ok(
                ApiResponse.<BudgetSuggestionResponse>builder()
                        .success(true)
                        .message("Budget suggestions retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{budgetId}/forecast")
    @Operation(
            summary = "Get budget forecast",
            description = "Predicts the expected budget utilization by the end of the budget period based on the current spending rate."
    )
    public ResponseEntity<ApiResponse<BudgetForecastResponse>> getBudgetForecast(
            @PathVariable Long budgetId) {

        BudgetForecastResponse response =
                budgetService.getBudgetForecast(budgetId);

        return ResponseEntity.ok(
                ApiResponse.<BudgetForecastResponse>builder()
                        .success(true)
                        .message("Budget forecast retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

}