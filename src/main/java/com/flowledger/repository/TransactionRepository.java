package com.flowledger.repository;

import com.flowledger.entity.Transaction;
import com.flowledger.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long>,
        JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    Optional<Transaction> findByIdAndUser(Long id, User user);

    long deleteByIdAndUser(Long id, User user);

    long countByUser(User user);

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