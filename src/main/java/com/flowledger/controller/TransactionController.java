package com.flowledger.controller;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.TransactionFilterRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.TransactionResponse;
import com.flowledger.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flowledger.dto.response.SpendingInsightsResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {

        TransactionResponse response =
                transactionService.createTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<TransactionResponse>builder()
                                .success(true)
                                .message("Transaction created successfully")
                                .data(response)
                                .build()
                );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getMyTransactions(

            @ParameterObject
            @ModelAttribute
            TransactionFilterRequest filter,

            @ParameterObject
            @PageableDefault(
                    size = 10,
                    sort = {"transactionTimestamp", "id"},
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        Page<TransactionResponse> transactions =
                transactionService.getMyTransactions(
                        filter,
                        pageable
                );

        ApiResponse<Page<TransactionResponse>> response =
                ApiResponse.<Page<TransactionResponse>>builder()
                        .success(true)
                        .message("Transactions retrieved successfully")
                        .data(transactions)
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(
            @PathVariable Long id) {

        TransactionResponse response =
                transactionService.getTransactionById(id);

        return ResponseEntity.ok(
                ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Transaction fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request) {

        TransactionResponse response =
                transactionService.updateTransaction(id, request);

        return ResponseEntity.ok(
                ApiResponse.<TransactionResponse>builder()
                        .success(true)
                        .message("Transaction updated successfully")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(
            @PathVariable Long id) {

        transactionService.deleteTransaction(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Transaction deleted successfully")
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> searchTransactions(
            @RequestParam(name = "title") String title) {

        List<TransactionResponse> transactions =
                transactionService.searchTransactions(title);

        ApiResponse<List<TransactionResponse>> response =
                ApiResponse.<List<TransactionResponse>>builder()
                        .success(true)
                        .message("Transactions retrieved successfully")
                        .data(transactions)
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/insights")
    @Operation(
            summary = "Get spending insights",
            description = "Provides spending analytics for the authenticated user's current month expenses."
    )
    public ResponseEntity<ApiResponse<SpendingInsightsResponse>> getSpendingInsights() {

        SpendingInsightsResponse response =
                transactionService.getSpendingInsights();

        return ResponseEntity.ok(
                ApiResponse.<SpendingInsightsResponse>builder()
                        .success(true)
                        .message("Spending insights retrieved successfully.")
                        .data(response)
                        .build()
        );
    }
}