package com.flowledger.service;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;

public interface TransactionService {

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );
}
