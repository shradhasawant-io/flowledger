package com.flowledger.repository;

import com.flowledger.dto.projection.ExpenseCategorySummaryProjection;
import com.flowledger.dto.projection.MonthlySummaryProjection;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.flowledger.dto.response.DashboardSummaryResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long>,
        JpaSpecificationExecutor<Transaction> {

    @Query("""
SELECT COALESCE(SUM(t.amount), 0)
FROM Transaction t
WHERE t.user = :user
AND t.type = com.flowledger.enums.TransactionType.INCOME
""")
    BigDecimal getTotalIncome(@Param("user") User user);

    @Query("""
SELECT COALESCE(SUM(t.amount), 0)
FROM Transaction t
WHERE t.user = :user
AND t.type = com.flowledger.enums.TransactionType.EXPENSE
""")
    BigDecimal getTotalExpense(@Param("user") User user);

    Long countByUser(User user);

    @Query("""
SELECT
    YEAR(t.transactionDate) AS year,
    MONTH(t.transactionDate) AS month,
    SUM(CASE
            WHEN t.type = com.flowledger.enums.TransactionType.INCOME
            THEN t.amount
            ELSE 0
        END) AS income,
    SUM(CASE
            WHEN t.type = com.flowledger.enums.TransactionType.EXPENSE
            THEN t.amount
            ELSE 0
        END) AS expense
FROM Transaction t
WHERE t.user = :user
GROUP BY
    YEAR(t.transactionDate),
    MONTH(t.transactionDate)
ORDER BY
    YEAR(t.transactionDate),
    MONTH(t.transactionDate)
""")
    List<MonthlySummaryProjection> getMonthlySummary(
            @Param("user") User user
    );

    @Query("""
SELECT
    t.category AS category,
    SUM(t.amount) AS totalExpense
FROM Transaction t
WHERE t.user = :user
  AND t.type = com.flowledger.enums.TransactionType.EXPENSE
GROUP BY t.category
ORDER BY SUM(t.amount) DESC
""")
    List<ExpenseCategorySummaryProjection> getExpenseByCategory(
            @Param("user") User user
    );

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    Optional<Transaction> findByIdAndUser(Long id, User user);

    long deleteByIdAndUser(Long id, User user);

    List<Transaction> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title
    );

    Page<Transaction> findByUser(
            User user,
            Pageable pageable
    );

    //void deleteByIdAndUser(Long id, User user);

}