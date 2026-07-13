package com.flowledger.service;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.TransactionFilterRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    void deleteTransaction(Long id);

    Page<TransactionResponse> getMyTransactions(
            TransactionFilterRequest filter,
            Pageable pageable
    );

    List<TransactionResponse> getMyTransactions();

    List<TransactionResponse> searchTransactions(String title);

    TransactionResponse getTransactionById(Long id);

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );

    TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request
    );
}
