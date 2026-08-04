package com.flowledger.dto.response;

import com.flowledger.enums.ReminderPriority;
import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private TransactionCategory category;

    private LocalDate dueDate;

    private Long daysRemaining;

    private ReminderPriority priority;

    private String reminderMessage;

    private Boolean unread;

    private LocalDateTime readAt;
}
