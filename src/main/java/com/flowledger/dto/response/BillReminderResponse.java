package com.flowledger.dto.response;

import com.flowledger.enums.ReminderPriority;
import com.flowledger.enums.ReminderStatus;
import com.flowledger.enums.TransactionCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillReminderResponse {

    private Long id;

    private String title;

    private BigDecimal amount;

    private TransactionCategory category;

    private LocalDate dueDate;

    private Long daysRemaining;

    private ReminderPriority priority;

    private ReminderStatus status;

    private Boolean notificationSent;

    private String reminderMessage;


}
