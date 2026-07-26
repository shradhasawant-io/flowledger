package com.flowledger.service;

import com.flowledger.dto.request.RecurringTransactionRequest;
import com.flowledger.dto.response.RecurringTransactionResponse;

import java.util.List;

public interface RecurringTransactionService {

    RecurringTransactionResponse createRecurringTransaction(
            RecurringTransactionRequest request
    );

    List<RecurringTransactionResponse> getRecurringTransactions();

    RecurringTransactionResponse getRecurringTransaction(
            Long recurringTransactionId
    );

    RecurringTransactionResponse updateRecurringTransaction(
            Long recurringTransactionId,
            RecurringTransactionRequest request
    );

    void deleteRecurringTransaction(
            Long recurringTransactionId
    );


}
