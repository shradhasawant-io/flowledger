package com.flowledger.controller;

import com.flowledger.dto.request.RecurringTransactionRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.RecurringTransactionResponse;
import com.flowledger.service.RecurringTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recurring-transactions")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Recurring Transactions",
        description = "Recurring Transaction Management APIs")
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    @PostMapping
    @Operation(summary = "Create recurring transaction")
    public ResponseEntity<ApiResponse<RecurringTransactionResponse>>
    createRecurringTransaction(
            @Valid @RequestBody RecurringTransactionRequest request) {

        RecurringTransactionResponse response =
                recurringTransactionService.createRecurringTransaction(request);

        return ResponseEntity.ok(
                ApiResponse.<RecurringTransactionResponse>builder()
                        .success(true)
                        .message("Recurring transaction created successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all recurring transactions")
    public ResponseEntity<ApiResponse<List<RecurringTransactionResponse>>>
    getRecurringTransactions() {

        List<RecurringTransactionResponse> response =
                recurringTransactionService.getRecurringTransactions();

        return ResponseEntity.ok(
                ApiResponse.<List<RecurringTransactionResponse>>builder()
                        .success(true)
                        .message("Recurring transactions retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{recurringTransactionId}")
    @Operation(summary = "Get recurring transaction by ID")
    public ResponseEntity<ApiResponse<RecurringTransactionResponse>>
    getRecurringTransaction(
            @PathVariable Long recurringTransactionId) {

        RecurringTransactionResponse response =
                recurringTransactionService.getRecurringTransaction(
                        recurringTransactionId
                );

        return ResponseEntity.ok(
                ApiResponse.<RecurringTransactionResponse>builder()
                        .success(true)
                        .message("Recurring transaction retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{recurringTransactionId}")
    @Operation(summary = "Update recurring transaction")
    public ResponseEntity<ApiResponse<RecurringTransactionResponse>>
    updateRecurringTransaction(
            @PathVariable Long recurringTransactionId,
            @Valid @RequestBody RecurringTransactionRequest request) {

        RecurringTransactionResponse response =
                recurringTransactionService.updateRecurringTransaction(
                        recurringTransactionId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.<RecurringTransactionResponse>builder()
                        .success(true)
                        .message("Recurring transaction updated successfully.")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{recurringTransactionId}")
    @Operation(summary = "Delete recurring transaction")
    public ResponseEntity<ApiResponse<Void>>
    deleteRecurringTransaction(
            @PathVariable Long recurringTransactionId) {

        recurringTransactionService.deleteRecurringTransaction(
                recurringTransactionId
        );

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Recurring transaction deleted successfully.")
                        .build()
        );
    }

}
