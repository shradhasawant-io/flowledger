package com.flowledger.service.impl;

import com.flowledger.dto.projection.MonthlySummaryProjection;
import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.ExpenseCategorySummaryResponse;
import com.flowledger.dto.response.MonthlySummaryResponse;
import com.flowledger.dto.response.dashboard.HighestExpenseResponse;
import com.flowledger.dto.response.dashboard.HighestIncomeResponse;
import com.flowledger.dto.response.dashboard.RecentTransactionResponse;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.enums.TransactionType;
import com.flowledger.exception.ResourceNotFoundException;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flowledger.enums.MonthlyStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.List;
import com.flowledger.dto.response.dashboard.DashboardStatisticsResponse;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TransactionRepository transactionRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary() {

        User currentUser = authenticatedUserService.getCurrentUser();

        BigDecimal totalIncome = transactionRepository.getTotalIncome(currentUser);

        BigDecimal totalExpense = transactionRepository.getTotalExpense(currentUser);

        Long totalTransactions = transactionRepository.countByUser(currentUser);

        BigDecimal currentBalance = totalIncome.subtract(totalExpense);

        return DashboardSummaryResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .currentBalance(currentBalance)
                .totalTransactions(totalTransactions)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlySummaryResponse> getMonthlySummary() {

        User currentUser = authenticatedUserService.getCurrentUser();

        List<MonthlySummaryProjection> monthlySummary =
                transactionRepository.getMonthlySummary(currentUser);

        return monthlySummary.stream()
                .map(summary -> {

                    BigDecimal income = summary.getIncome();
                    BigDecimal expense = summary.getExpense();

                    BigDecimal saving = income.subtract(expense);

                    BigDecimal savingRate;

                    if (income.compareTo(BigDecimal.ZERO) == 0) {
                        savingRate = BigDecimal.ZERO;
                    } else {
                        savingRate = saving
                                .multiply(BigDecimal.valueOf(100))
                                .divide(income, 2, RoundingMode.HALF_UP);
                    }

                    MonthlyStatus status;

                    if (saving.compareTo(BigDecimal.ZERO) > 0) {
                        status = MonthlyStatus.PROFIT;
                    } else if (saving.compareTo(BigDecimal.ZERO) < 0) {
                        status = MonthlyStatus.LOSS;
                    } else {
                        status = MonthlyStatus.BREAK_EVEN;
                    }

                    return MonthlySummaryResponse.builder()
                            .year(summary.getYear())
                            .month(Month.of(summary.getMonth()))
                            .income(income)
                            .expense(expense)
                            .saving(saving)
                            .savingRate(savingRate)
                            .status(status)
                            .build();

                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategorySummaryResponse> getExpenseByCategory() {

        User currentUser = authenticatedUserService.getCurrentUser();

        return transactionRepository
                .getExpenseByCategory(currentUser)
                .stream()
                .map(summary -> ExpenseCategorySummaryResponse.builder()
                        .category(summary.getCategory())
                        .totalExpense(summary.getTotalExpense())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecentTransactionResponse> getRecentTransactions() {

        User currentUser = authenticatedUserService.getCurrentUser();

        List<Transaction> transactions =
                transactionRepository.findTop5ByUserOrderByTransactionTimestampDesc(currentUser);

        return transactions.stream()
                .map(transaction -> RecentTransactionResponse.builder()
                        .id(transaction.getId())
                        .title(transaction.getTitle())
                        .amount(transaction.getAmount())
                        .type(transaction.getType())
                        .category(transaction.getCategory())
                        .paymentMethod(transaction.getPaymentMethod())
                        .transactionTimestamp(transaction.getTransactionTimestamp())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HighestExpenseResponse getHighestExpense() {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionRepository
                .findTopByUserAndTypeOrderByAmountDesc(
                        currentUser,
                        TransactionType.EXPENSE
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No expense transactions found."
                ));

        return HighestExpenseResponse.builder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .amount(transaction.getAmount())
                .category(transaction.getCategory())
                .paymentMethod(transaction.getPaymentMethod())
                .transactionTimestamp(transaction.getTransactionTimestamp())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public HighestIncomeResponse getHighestIncome() {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction transaction = transactionRepository
                .findTopByUserAndTypeOrderByAmountDesc(
                        currentUser,
                        TransactionType.INCOME
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No income transactions found."
                ));

        return HighestIncomeResponse.builder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .amount(transaction.getAmount())
                .category(transaction.getCategory())
                .paymentMethod(transaction.getPaymentMethod())
                .transactionTimestamp(transaction.getTransactionTimestamp())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatisticsResponse getDashboardStatistics() {

        User currentUser = authenticatedUserService.getCurrentUser();

        Transaction highestIncome = transactionRepository
                .findTopByUserAndTypeOrderByAmountDesc(
                        currentUser,
                        TransactionType.INCOME
                )
                .orElse(null);

        Transaction highestExpense = transactionRepository
                .findTopByUserAndTypeOrderByAmountDesc(
                        currentUser,
                        TransactionType.EXPENSE
                )
                .orElse(null);

        List<MonthlySummaryProjection> monthlySummary =
                transactionRepository.getMonthlySummary(currentUser);

        BigDecimal totalSavings = monthlySummary.stream()
                .map(summary -> summary.getIncome().subtract(summary.getExpense()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageMonthlyIncome;

        if (monthlySummary.isEmpty()) {
            averageMonthlyIncome = BigDecimal.ZERO;
        } else {
            averageMonthlyIncome = monthlySummary.stream()
                    .map(MonthlySummaryProjection::getIncome)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(
                            BigDecimal.valueOf(monthlySummary.size()),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        BigDecimal averageMonthlyExpense;

        if (monthlySummary.isEmpty()) {
            averageMonthlyExpense = BigDecimal.ZERO;
        } else {
            averageMonthlyExpense = monthlySummary.stream()
                    .map(MonthlySummaryProjection::getExpense)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(
                            BigDecimal.valueOf(monthlySummary.size()),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        BigDecimal averageSaving;

        if (monthlySummary.isEmpty()) {
            averageSaving = BigDecimal.ZERO;
        } else {
            averageSaving = totalSavings.divide(
                    BigDecimal.valueOf(monthlySummary.size()),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        int profitableMonths = (int) monthlySummary.stream()
                .filter(summary ->
                        summary.getIncome()
                                .subtract(summary.getExpense())
                                .compareTo(BigDecimal.ZERO) > 0
                )
                .count();

        int lossMonths = (int) monthlySummary.stream()
                .filter(summary ->
                        summary.getIncome()
                                .subtract(summary.getExpense())
                                .compareTo(BigDecimal.ZERO) < 0
                )
                .count();

        Month bestMonth = monthlySummary.stream()
                .max((m1, m2) -> m1.getIncome()
                        .subtract(m1.getExpense())
                        .compareTo(
                                m2.getIncome().subtract(m2.getExpense())
                        ))
                .map(summary -> Month.of(summary.getMonth()))
                .orElse(null);

        Month worstMonth = monthlySummary.stream()
                .min((m1, m2) -> m1.getIncome()
                        .subtract(m1.getExpense())
                        .compareTo(
                                m2.getIncome().subtract(m2.getExpense())
                        ))
                .map(summary -> Month.of(summary.getMonth()))
                .orElse(null);

        return DashboardStatisticsResponse.builder()
                .highestIncome(
                        highestIncome != null
                                ? highestIncome.getAmount()
                                : BigDecimal.ZERO
                )
                .highestExpense(
                        highestExpense != null
                                ? highestExpense.getAmount()
                                : BigDecimal.ZERO
                )
                .totalSavings(totalSavings)
                .averageMonthlyIncome(averageMonthlyIncome)
                .averageMonthlyExpense(averageMonthlyExpense)
                .averageSaving(averageSaving)
                .profitableMonths(profitableMonths)
                .lossMonths(lossMonths)
                .bestMonth(bestMonth)
                .worstMonth(worstMonth)
                .build();

    }
}