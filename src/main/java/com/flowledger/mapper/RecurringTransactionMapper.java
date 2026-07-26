package com.flowledger.mapper;

import com.flowledger.dto.request.RecurringTransactionRequest;
import com.flowledger.dto.response.RecurringTransactionResponse;
import com.flowledger.entity.RecurringTransaction;
import org.springframework.stereotype.Component;

@Component
public class RecurringTransactionMapper {

    public RecurringTransaction toEntity(
            RecurringTransactionRequest request) {

        RecurringTransaction recurringTransaction =
                new RecurringTransaction();

        recurringTransaction.setTitle(request.getTitle());
        recurringTransaction.setDescription(request.getDescription());
        recurringTransaction.setAmount(request.getAmount());
        recurringTransaction.setType(request.getType());
        recurringTransaction.setCategory(request.getCategory());
        recurringTransaction.setFrequency(request.getFrequency());
        recurringTransaction.setStartDate(request.getStartDate());
        recurringTransaction.setPaymentMethod(request.getPaymentMethod());

        return recurringTransaction;
    }

    public RecurringTransactionResponse toResponse(
            RecurringTransaction recurringTransaction) {

        return RecurringTransactionResponse.builder()
                .id(recurringTransaction.getId())
                .title(recurringTransaction.getTitle())
                .description(recurringTransaction.getDescription())
                .amount(recurringTransaction.getAmount())
                .type(recurringTransaction.getType())
                .category(recurringTransaction.getCategory())
                .frequency(recurringTransaction.getFrequency())
                .startDate(recurringTransaction.getStartDate())
                .nextExecutionDate(recurringTransaction.getNextExecutionDate())
                .active(recurringTransaction.getActive())
                .paymentMethod(recurringTransaction.getPaymentMethod())
                .build();
    }

    public void updateEntity(
            RecurringTransaction recurringTransaction,
            RecurringTransactionRequest request) {

        recurringTransaction.setTitle(request.getTitle());
        recurringTransaction.setDescription(request.getDescription());
        recurringTransaction.setAmount(request.getAmount());
        recurringTransaction.setType(request.getType());
        recurringTransaction.setCategory(request.getCategory());
        recurringTransaction.setFrequency(request.getFrequency());
        recurringTransaction.setStartDate(request.getStartDate());
        recurringTransaction.setPaymentMethod(request.getPaymentMethod());
    }
}