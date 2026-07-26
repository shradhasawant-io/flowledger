package com.flowledger.scheduler;

import com.flowledger.entity.RecurringTransaction;
import com.flowledger.entity.Transaction;
import com.flowledger.repository.RecurringTransactionRepository;
import com.flowledger.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringTransactionScheduler {

    private final RecurringTransactionRepository recurringTransactionRepository;

    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void processRecurringTransactions() {

        log.info("Recurring transaction scheduler started.");

        LocalDate today = LocalDate.now();

        List<RecurringTransaction> dueRecurringTransactions =
                recurringTransactionRepository.findDueRecurringTransactions(today);

        log.info("Found {} recurring transactions due today.",
                dueRecurringTransactions.size());

        if (dueRecurringTransactions.isEmpty()) {
            log.info("No recurring transactions to process.");
            return;
        }

        for (RecurringTransaction recurringTransaction
                : dueRecurringTransactions) {

            processRecurringTransaction(recurringTransaction);
        }
    }

    private void processRecurringTransaction(
            RecurringTransaction recurringTransaction) {

        Transaction transaction =
                createTransaction(recurringTransaction);

        transactionRepository.save(transaction);

        recurringTransaction.setNextExecutionDate(
                calculateNextExecutionDate(recurringTransaction)
        );

        recurringTransactionRepository.save(recurringTransaction);

        log.info(
                "Processed recurring transaction: {}",
                recurringTransaction.getTitle()
        );
    }

    private Transaction createTransaction(
            RecurringTransaction recurringTransaction){

        Transaction transaction = new Transaction();

        transaction.setTitle(
                recurringTransaction.getTitle()
        );

        transaction.setAmount(
                recurringTransaction.getAmount()
        );

        transaction.setType(
                recurringTransaction.getType()
        );

        transaction.setCategory(
                recurringTransaction.getCategory()
        );

        transaction.setUser(
                recurringTransaction.getUser()
        );

        transaction.setTransactionTimestamp(
                LocalDateTime.now()
        );

        transaction.setPaymentMethod(recurringTransaction.getPaymentMethod());

        transaction.setNotes(
                "Generated from recurring transaction."
        );

        return transaction;

    }

    private LocalDate calculateNextExecutionDate(
            RecurringTransaction recurringTransaction){

        LocalDate nextExecutionDate;

        switch (recurringTransaction.getFrequency()) {

            case DAILY ->
                    nextExecutionDate =
                            recurringTransaction.getNextExecutionDate().plusDays(1);

            case WEEKLY ->
                    nextExecutionDate =
                            recurringTransaction.getNextExecutionDate().plusWeeks(1);

            case MONTHLY ->
                    nextExecutionDate =
                            recurringTransaction.getNextExecutionDate().plusMonths(1);

            case YEARLY ->
                    nextExecutionDate =
                            recurringTransaction.getNextExecutionDate().plusYears(1);

            default ->
                    throw new IllegalStateException(
                            "Unsupported recurring frequency."
                    );
        }

        return nextExecutionDate;

    }

}


