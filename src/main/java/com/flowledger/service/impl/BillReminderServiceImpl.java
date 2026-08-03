package com.flowledger.service.impl;

import com.flowledger.dto.response.BillReminderResponse;
import com.flowledger.entity.BillReminder;
import com.flowledger.entity.User;
import com.flowledger.enums.ReminderStatus;
import com.flowledger.mapper.BillReminderMapper;
import com.flowledger.repository.BillReminderRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.BillReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BillReminderServiceImpl
        implements BillReminderService {

    private final BillReminderRepository billReminderRepository;

    private final BillReminderMapper billReminderMapper;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional(readOnly = true)
    public List<BillReminderResponse> getPendingReminders() {

        User currentUser = authenticatedUserService.getCurrentUser();

        List<BillReminder> reminders =
                billReminderRepository
                        .findByUserAndStatusOrderByDueDateAsc(
                                currentUser,
                                ReminderStatus.PENDING
                        );
        return reminders.stream()
                .map(billReminderMapper::toResponse)
                .toList();
    }

    @Override
    public BillReminderResponse dismissReminder(Long reminderId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        BillReminder billReminder =
                billReminderRepository
                        .findByIdAndUser(
                                reminderId,
                                currentUser
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Bill reminder not found."
                                )
                        );

        billReminder.setStatus(ReminderStatus.DISMISSED);

        BillReminder updatedReminder = billReminderRepository.save(billReminder);

        return billReminderMapper.toResponse(updatedReminder);
    }
}


