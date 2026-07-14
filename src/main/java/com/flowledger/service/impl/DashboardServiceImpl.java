package com.flowledger.service.impl;

import com.flowledger.dto.projection.MonthlySummaryProjection;
import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.dto.response.MonthlySummaryResponse;
import com.flowledger.entity.User;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

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
                .map(summary -> MonthlySummaryResponse.builder()
                        .year(summary.getYear())
                        .month(Month.of(summary.getMonth()))
                        .income(summary.getIncome())
                        .expense(summary.getExpense())
                        .build())
                .toList();
    }
}