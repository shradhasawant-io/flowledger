package com.flowledger.dto.response;

import com.flowledger.enums.ReminderPriority;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardNotificationResponse {

    private Long id;

    private String title;

    private LocalDate dueDate;

    private Long daysRemaining;

    private ReminderPriority priority;

}
