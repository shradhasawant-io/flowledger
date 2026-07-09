package com.flowledger.service;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getMyTransactions();

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );
}
