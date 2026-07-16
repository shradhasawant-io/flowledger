package com.flowledger.service.impl;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.TransactionFilterRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.TransactionResponse;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.exception.ResourceNotFoundException;
import com.flowledger.mapper.TransactionMapper;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.TransactionService;
import com.flowledger.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flowledger.enums.TransactionCategory;
import com.flowledger.enums.TransactionType;
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

        validateCategory(
                request.getType(),
                request.getCategory()
        );

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
                .findByUserOrderByTransactionTimestampDesc(currentUser)
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

        validateCategory(
                request.getType(),
                request.getCategory()
        );

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

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> searchTransactions(String title) {

        User currentUser = authenticatedUserService.getCurrentUser();

        List<Transaction> transactions =
                transactionRepository.findByUserAndTitleContainingIgnoreCase(
                        currentUser,
                        title
                );

        return transactions.stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getMyTransactions(
            TransactionFilterRequest filter,
            Pageable pageable
    ) {
        User currentUser = authenticatedUserService.getCurrentUser();

        Specification<Transaction> specification =
                Specification.<Transaction>unrestricted()
                        .and(TransactionSpecification.belongsToUser(currentUser))
                        .and(TransactionSpecification.hasType(filter.getType()))
                        .and(TransactionSpecification.hasPaymentMethod(filter.getPaymentMethod()))
                        .and(TransactionSpecification.hasStartDate(filter.getStartDate()))
                        .and(TransactionSpecification.hasEndDate(filter.getEndDate()));

        Page<Transaction> transactions =
                transactionRepository.findAll(specification, pageable);

        return transactions.map(transactionMapper::toResponse);

    }

    private void validateCategory(
            TransactionType type,
            TransactionCategory category
    ) {

        if (category.getTransactionType() != type) {
            throw new IllegalArgumentException(
                    "Category " + category +
                            " cannot be used with " + type + " transactions."
            );
        }
    }
}
