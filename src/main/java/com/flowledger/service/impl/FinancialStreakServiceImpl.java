package com.flowledger.service.impl;

import com.flowledger.dto.response.FinancialStreakResponse;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.enums.TransactionType;
import com.flowledger.repository.TransactionRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.FinancialStreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinancialStreakServiceImpl
        implements FinancialStreakService {

    private final TransactionRepository transactionRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public FinancialStreakResponse getFinancialStreaks() {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        List<Transaction> transactions = transactionRepository.findByUserOrderByTransactionTimestampAsc(currentUser);

        Map<LocalDate, List<Transaction>> transactionsByDate =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                transaction ->
                                        transaction.getTransactionTimestamp()
                                                .toLocalDate()
                        ));

        List<LocalDate> transactionDates =
                transactionsByDate.keySet()
                        .stream()
                        .sorted()
                        .toList();

        if (transactionDates.isEmpty()) {

            return FinancialStreakResponse.builder()
                    .currentLoggingStreak(0)
                    .longestLoggingStreak(0)
                    .currentNoSpendStreak(0)
                    .longestNoSpendStreak(0)
                    .motivationalMessage("Start tracking your finances today!")
                    .build();
        }

        int currentLoggingStreak =
                calculateCurrentLoggingStreak(transactionsByDate);

        int longestLoggingStreak =
                calculateLongestLoggingStreak(transactionDates);

        int currentNoSpendStreak =
                calculateCurrentNoSpendStreak(transactionsByDate);

        int longestNoSpendStreak =
                calculateLongestNoSpendStreak(
                        transactionsByDate,
                        transactionDates
                );

        String motivationalMessage =
                generateMotivationalMessage(
                        currentLoggingStreak,
                        currentNoSpendStreak
                );

        return FinancialStreakResponse.builder()
                .currentLoggingStreak(currentLoggingStreak)
                .longestLoggingStreak(longestLoggingStreak)
                .currentNoSpendStreak(currentNoSpendStreak)
                .longestNoSpendStreak(longestNoSpendStreak)
                .motivationalMessage(motivationalMessage)
                .build();
    }

    private int calculateCurrentLoggingStreak(
            Map<LocalDate, List<Transaction>> transactionsByDate) {

        int currentLoggingStreak = 0;

        LocalDate currentDate = LocalDate.now();

        while (transactionsByDate.containsKey(currentDate)) {

            currentLoggingStreak++;

            currentDate = currentDate.minusDays(1);

        }

        return currentLoggingStreak;
    }

    private int calculateLongestLoggingStreak(
            List<LocalDate> transactionDates) {

        int longestLoggingStreak = 1;
        int currentLongestRun = 1;

        for (int i = 1; i < transactionDates.size(); i++) {

            LocalDate previousDate = transactionDates.get(i - 1);
            LocalDate currentTransactionDate = transactionDates.get(i);

            if (previousDate.plusDays(1).equals(currentTransactionDate)) {

                currentLongestRun++;

            } else {

                currentLongestRun = 1;

            }

            longestLoggingStreak =
                    Math.max(longestLoggingStreak, currentLongestRun);
        }

        return longestLoggingStreak;
    }


    private int calculateCurrentNoSpendStreak(
            Map<LocalDate, List<Transaction>> transactionsByDate) {

        int currentNoSpendStreak = 0;

        LocalDate noSpendDate = LocalDate.now();

        while (transactionsByDate.containsKey(noSpendDate)) {

            List<Transaction> dailyTransactions =
                    transactionsByDate.get(noSpendDate);

            boolean hasExpense =
                    dailyTransactions.stream()
                            .anyMatch(transaction ->
                                    transaction.getType() == TransactionType.EXPENSE);

            if (hasExpense) {
                break;
            }

            currentNoSpendStreak++;

            noSpendDate = noSpendDate.minusDays(1);
        }

        return currentNoSpendStreak;
    }

    private int calculateLongestNoSpendStreak(
            Map<LocalDate, List<Transaction>> transactionsByDate,
            List<LocalDate> transactionDates) {
        int longestNoSpendStreak = 0;
        int currentNoSpendRun = 0;

        for (LocalDate transactionDate : transactionDates) {

            List<Transaction> dailyTransactions =
                    transactionsByDate.get(transactionDate);

            boolean hasExpense =
                    dailyTransactions.stream()
                            .anyMatch(transaction ->
                                    transaction.getType() == TransactionType.EXPENSE);

            if (!hasExpense) {

                currentNoSpendRun++;

                longestNoSpendStreak =
                        Math.max(longestNoSpendStreak, currentNoSpendRun);

            } else {

                currentNoSpendRun = 0;

            }
        }

        return longestNoSpendStreak;
    }

    private String generateMotivationalMessage(
            int currentLoggingStreak,
            int currentNoSpendStreak) {

        String motivationalMessage;

        if (currentLoggingStreak >= 30) {

            motivationalMessage =
                    "🔥 Amazing! You're on a 30+ day expense logging streak!";

        } else if (currentLoggingStreak >= 7) {

            motivationalMessage =
                    "👏 Great job! Keep your expense logging streak alive.";

        } else if (currentNoSpendStreak >= 5) {

            motivationalMessage =
                    "💰 Fantastic! You're maintaining an impressive no-spend streak.";

        } else if (currentLoggingStreak > 0) {

            motivationalMessage =
                    "📈 Keep tracking your expenses every day.";

        } else {

            motivationalMessage =
                    "Start logging transactions today to build your first streak!";
        }

        return motivationalMessage;
    }
}