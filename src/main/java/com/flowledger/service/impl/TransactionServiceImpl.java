package com.flowledger.service.impl;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.exception.ResourceNotFoundException;
import com.flowledger.mapper.TransactionMapper;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionResponse createTransaction(
            CreateTransactionRequest request) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionMapper.toEntity(request);

        transaction.setUser(currentUser);

        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getMyTransactions() {

        User currentUser = authenticatedUserService.getCurrentUser();

        return transactionRepository
                .findByUserOrderByTransactionDateDesc(currentUser)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long id) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public TransactionResponse updateTransaction(
            Long id,
            UpdateTransactionRequest request) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        transactionMapper.updateEntity(transaction, request);

        Transaction updated = transactionRepository.save(transaction);

        return transactionMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long id) {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionRepository
                .findByIdAndUser(id, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transaction not found"));

        transactionRepository.delete(transaction);
    }
}
