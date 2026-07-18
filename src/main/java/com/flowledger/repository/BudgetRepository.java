package com.flowledger.repository;

import com.flowledger.entity.Budget;
import com.flowledger.entity.User;
import com.flowledger.enums.BudgetPeriod;
import com.flowledger.enums.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUser(User user);

    boolean existsByUserAndCategoryAndPeriod(
            User user,
            TransactionCategory category,
            BudgetPeriod period
    );

    Optional<Budget> findByIdAndUser(
            Long id,
            User user
    );

    boolean existsByUserAndCategoryAndPeriodAndIdNot(
            User user,
            TransactionCategory category,
            BudgetPeriod period,
            Long id
    );

}