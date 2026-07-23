package com.flowledger.repository;

import com.flowledger.dto.projection.ExpenseCategorySummaryProjection;
import com.flowledger.dto.projection.MonthlySummaryProjection;
import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import com.flowledger.enums.TransactionCategory;
import com.flowledger.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.flowledger.dto.response.DashboardSummaryResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    YEAR(t.transactionTimestamp) AS year,
    MONTH(t.transactionTimestamp) AS month,
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
    YEAR(t.transactionTimestamp),
    MONTH(t.transactionTimestamp)
ORDER BY
    YEAR(t.transactionTimestamp),
    MONTH(t.transactionTimestamp)
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

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.type = :type
      AND t.category = :category
      AND t.transactionTimestamp BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalAmountByCategoryAndDateRange(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("category") TransactionCategory category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
SELECT t
FROM Transaction t
WHERE t.user = :user
  AND t.type = :type
  AND t.transactionTimestamp BETWEEN :startDate AND :endDate
ORDER BY t.transactionTimestamp ASC
""")
    List<Transaction> findTransactionsByUserAndTypeAndDateRange(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    List<Transaction> findTop5ByUserOrderByTransactionTimestampDesc(User user);

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserOrderByTransactionTimestampDesc(User user);

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

    Optional<Transaction> findTopByUserAndTypeOrderByAmountDesc(
            User user,
            TransactionType type
    );

    //void deleteByIdAndUser(Long id, User user);

}