package com.flowledger.repository;

import com.flowledger.entity.BillReminder;
import com.flowledger.entity.RecurringTransaction;
import com.flowledger.entity.User;
import com.flowledger.enums.ReminderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillReminderRepository
        extends JpaRepository<BillReminder, Long> {

    List<BillReminder> findByUserAndStatusOrderByDueDateAsc(
            User user,
            ReminderStatus status
    );

    Optional<BillReminder> findByIdAndUser(
            Long id,
            User user
    );

    boolean existsByRecurringTransactionAndDueDate(
            RecurringTransaction recurringTransaction,
            LocalDate dueDate
    );

    List<BillReminder> findByStatus(
            ReminderStatus status
    );

    List<BillReminder> findByDueDate(
            LocalDate dueDate
    );

    List<BillReminder> findByUserAndNotificationSentFalse(
            User user
    );

}
