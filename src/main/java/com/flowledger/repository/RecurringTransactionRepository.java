package com.flowledger.repository;

import com.flowledger.entity.RecurringTransaction;
import com.flowledger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecurringTransactionRepository
        extends JpaRepository<RecurringTransaction, Long> {

    List<RecurringTransaction> findByUser(User user);

    Optional<RecurringTransaction> findByIdAndUser(
            Long id,
            User user
    );

    long deleteByIdAndUser(
            Long id,
            User user
    );

    List<RecurringTransaction> findByUserAndActiveTrue(
            User user
    );

    @Query("""
SELECT r
FROM RecurringTransaction r
WHERE r.active = true
AND r.nextExecutionDate <= :today
""")
    List<RecurringTransaction> findDueRecurringTransactions(
            @Param("today") LocalDate today
    );
}
