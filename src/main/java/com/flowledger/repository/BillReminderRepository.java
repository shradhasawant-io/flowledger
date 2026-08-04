package com.flowledger.repository;

import com.flowledger.entity.BillReminder;
import com.flowledger.entity.RecurringTransaction;
import com.flowledger.entity.User;
import com.flowledger.enums.ReminderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    List<BillReminder> findByUserOrderByDueDateAsc(
            User user
    );

    long countByUserAndReadAtIsNull(
            User user
    );

    @Modifying
    @Query("""
UPDATE BillReminder b
SET b.readAt = :readAt
WHERE b.user = :user
AND b.readAt IS NULL
""")
    int markAllAsRead(
            @Param("user") User user,
            @Param("readAt") LocalDateTime readAt
    );

}
