package com.flowledger.service;

import com.flowledger.dto.response.BillReminderResponse;

import java.util.List;

public interface BillReminderService {

    List<BillReminderResponse> getPendingReminders();

    BillReminderResponse dismissReminder(
            Long reminderId
    );


}
