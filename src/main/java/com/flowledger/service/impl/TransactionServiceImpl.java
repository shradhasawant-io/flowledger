package com.flowledger.service.impl;

import com.flowledger.dto.request.CreateTransactionRequest;
import com.flowledger.dto.request.TransactionFilterRequest;
import com.flowledger.dto.request.UpdateTransactionRequest;
import com.flowledger.dto.response.SpendingInsightsResponse;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public SpendingInsightsResponse getSpendingInsights() {

        User currentUser = authenticatedUserService.getCurrentUser();

        LocalDate today = LocalDate.now();

        LocalDate startDate = today.withDayOfMonth(1);

        LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());

        List<Transaction> transactions =
                transactionRepository.findTransactionsByUserAndTypeAndDateRange(
                        currentUser,
                        TransactionType.EXPENSE,
                        startDate.atStartOfDay(),
                        endDate.atTime(LocalTime.MAX)
                );

        if (transactions.isEmpty()) {
            return SpendingInsightsResponse.builder()
                    .totalExpense(BigDecimal.ZERO)
                    .averageDailyExpense(BigDecimal.ZERO)
                    .highestCategoryAmount(BigDecimal.ZERO)
                    .lowestCategoryAmount(BigDecimal.ZERO)
                    .highestCategoryPercentage(BigDecimal.ZERO)
                    .totalExpenseTransactions(0)
                    .insightMessage("No expense transactions found for the current month.")
                    .build();
        }

        BigDecimal totalExpense = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<TransactionCategory, BigDecimal> categoryTotals =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getCategory,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        Transaction::getAmount,
                                        BigDecimal::add
                                )
                        ));

        Map.Entry<TransactionCategory, BigDecimal> highestCategory =
                categoryTotals.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();

        TransactionCategory highestCategoryName =
                highestCategory.getKey();

        BigDecimal highestCategoryAmount =
                highestCategory.getValue();

        Map.Entry<TransactionCategory, BigDecimal> lowestCategory =
                categoryTotals.entrySet()
                        .stream()
                        .min(Map.Entry.comparingByValue())
                        .orElseThrow();

        TransactionCategory lowestCategoryName =
                lowestCategory.getKey();

        BigDecimal lowestCategoryAmount =
                lowestCategory.getValue();

        long elapsedDays = ChronoUnit.DAYS.between(startDate, today) + 1;

        BigDecimal averageDailyExpense =
                totalExpense.divide(
                        BigDecimal.valueOf(elapsedDays),
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal highestCategoryPercentage =
                highestCategoryAmount
                        .multiply(BigDecimal.valueOf(100))
                        .divide(
                                totalExpense,
                                2,
                                RoundingMode.HALF_UP
                        );

        String insightMessage =
                highestCategoryName +
                        " accounts for " +
                        highestCategoryPercentage +
                        "% of your spending this month.";

        return SpendingInsightsResponse.builder()
                .totalExpense(totalExpense)
                .averageDailyExpense(averageDailyExpense)
                .highestSpendingCategory(highestCategoryName)
                .highestCategoryAmount(highestCategoryAmount)
                .lowestSpendingCategory(lowestCategoryName)
                .lowestCategoryAmount(lowestCategoryAmount)
                .highestCategoryPercentage(highestCategoryPercentage)
                .totalExpenseTransactions(transactions.size())
                .insightMessage(insightMessage)
                .build();
    }
}
