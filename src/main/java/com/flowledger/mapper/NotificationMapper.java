package com.flowledger.mapper;

import com.flowledger.dto.response.NotificationResponse;
import com.flowledger.entity.BillReminder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(
            BillReminder billReminder){

        long daysRemaining =
                ChronoUnit.DAYS.between(
                        LocalDate.now(),
                        billReminder.getDueDate()
                );

        boolean unread =
                billReminder.getReadAt() == null;

        String reminderMessage;

        if (daysRemaining == 0) {
            reminderMessage =
                    billReminder.getTitle() + " is due today.";
        }
        else if (daysRemaining == 1) {
            reminderMessage =
                    billReminder.getTitle() + " is due tomorrow.";
        }
        else {
            reminderMessage =
                    billReminder.getTitle()
                            + " is due in "
                            + daysRemaining
                            + " days.";
        }

        return NotificationResponse.builder()
                .id(billReminder.getId())
                .title(billReminder.getTitle())
                .amount(billReminder.getAmount())
                .category(billReminder.getCategory())
                .dueDate(billReminder.getDueDate())
                .daysRemaining(daysRemaining)
                .priority(billReminder.getPriority())
                .readAt(billReminder.getReadAt())
                .unread(unread)
                .reminderMessage(reminderMessage)
                .build();
    }
}
