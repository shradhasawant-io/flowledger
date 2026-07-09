package com.flowledger.controller;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.TransactionResponse;
import com.flowledger.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getMyTransactions() {

        List<TransactionResponse> response =
                transactionService.getMyTransactions();

        return ResponseEntity.ok(
                ApiResponse.<List<TransactionResponse>>builder()
                        .success(true)
                        .message("Transactions fetched successfully")
                        .data(response)
                        .build()
        );
    }
}