package com.flowledger.service.impl;

import com.flowledger.dto.request.RecurringTransactionRequest;
import com.flowledger.dto.response.RecurringTransactionResponse;
import com.flowledger.entity.RecurringTransaction;
import com.flowledger.entity.User;
import com.flowledger.mapper.RecurringTransactionMapper;
import com.flowledger.repository.RecurringTransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.RecurringTransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    private final RecurringTransactionRepository recurringTransactionRepository;

    private final RecurringTransactionMapper recurringTransactionMapper;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public RecurringTransactionResponse createRecurringTransaction(
            RecurringTransactionRequest request) {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        RecurringTransaction recurringTransaction =
                recurringTransactionMapper.toEntity(request);

        recurringTransaction.setUser(currentUser);

        recurringTransaction.setActive(true);

        recurringTransaction.setNextExecutionDate(
                request.getStartDate()
        );

        RecurringTransaction savedRecurringTransaction =
                recurringTransactionRepository.save(recurringTransaction);

        return recurringTransactionMapper.toResponse(
                savedRecurringTransaction
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecurringTransactionResponse> getRecurringTransactions() {
        User currentUser = authenticatedUserService.getCurrentUser();

        List<RecurringTransaction> recurringTransactions =
                recurringTransactionRepository.findByUser(currentUser);

        return recurringTransactions.stream()
                .map(recurringTransactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecurringTransactionResponse getRecurringTransaction(Long recurringTransactionId) {
        User currentUser =
                authenticatedUserService.getCurrentUser();

        RecurringTransaction recurringTransaction =
                recurringTransactionRepository
                        .findByIdAndUser(
                                recurringTransactionId,
                                currentUser
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Recurring transaction not found."
                                )
                        );

        return recurringTransactionMapper.toResponse(
                recurringTransaction
        );
    }

    @Override
    public RecurringTransactionResponse updateRecurringTransaction(
            Long recurringTransactionId,
            RecurringTransactionRequest request) {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        RecurringTransaction recurringTransaction =
                recurringTransactionRepository
                        .findByIdAndUser(
                                recurringTransactionId,
                                currentUser
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Recurring transaction not found."
                                )
                        );

        recurringTransactionMapper.updateEntity(
                recurringTransaction,
                request
        );

        recurringTransaction.setNextExecutionDate(
                request.getStartDate()
        );

        RecurringTransaction updatedRecurringTransaction =
                recurringTransactionRepository.save(
                        recurringTransaction
                );

        return recurringTransactionMapper.toResponse(
                updatedRecurringTransaction
        );
    }

    @Override
    public void deleteRecurringTransaction(Long recurringTransactionId) {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        long deletedRows =
                recurringTransactionRepository.deleteByIdAndUser(
                        recurringTransactionId,
                        currentUser
                );

        if (deletedRows == 0) {
            throw new IllegalArgumentException(
                    "Recurring transaction not found."
            );
        }
    }
}
