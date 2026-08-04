package com.flowledger.entity;

import com.flowledger.enums.ReminderPriority;
import com.flowledger.enums.ReminderStatus;
import com.flowledger.enums.TransactionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill_reminders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillReminder extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "recurring_transaction_id",
            nullable = false
    )
    private RecurringTransaction recurringTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean notificationSent;

    @Column
    private LocalDateTime readAt;
}
