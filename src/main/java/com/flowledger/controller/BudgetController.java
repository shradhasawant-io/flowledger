package com.flowledger.controller;

import com.flowledger.dto.request.CreateBudgetRequest;
import com.flowledger.dto.request.UpdateBudgetRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.BudgetProgressResponse;
import com.flowledger.dto.response.BudgetResponse;
import com.flowledger.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

}