package com.flowledger.service;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    void deleteTransaction(Long id);

    List<TransactionResponse> getMyTransactions();

    TransactionResponse getTransactionById(Long id);

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );

    TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request
    );
}
