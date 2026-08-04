package com.flowledger.service.impl;

import com.flowledger.dto.response.NotificationCountResponse;
import com.flowledger.dto.response.NotificationResponse;
import com.flowledger.entity.BillReminder;
import com.flowledger.entity.User;
import com.flowledger.mapper.NotificationMapper;
import com.flowledger.repository.BillReminderRepository;
import com.flowledger.service.AuthenticatedUserService;
import com.flowledger.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl
        implements NotificationService {

    private final BillReminderRepository billReminderRepository;

    private final NotificationMapper notificationMapper;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications() {

        User currentUser = authenticatedUserService.getCurrentUser();

        List<BillReminder> reminders =
                billReminderRepository.findByUserOrderByDueDateAsc(
                        currentUser
                );

        return reminders.stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    public NotificationResponse markAsRead(Long notificationId) {

        User currentUser = authenticatedUserService.getCurrentUser();

        BillReminder billReminder =
                billReminderRepository
                        .findByIdAndUser(
                                notificationId,
                                currentUser
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Notification not found."
                                )
                        );

        billReminder.setReadAt(
                LocalDateTime.now()
        );

        return notificationMapper.toResponse(billReminder);
    }

    @Override
    public void markAllAsRead() {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        billReminderRepository.markAllAsRead(
                currentUser,
                LocalDateTime.now()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationCountResponse getUnreadCount() {

        User currentUser =
                authenticatedUserService.getCurrentUser();

        long unreadCount =
                billReminderRepository.countByUserAndReadAtIsNull(
                        currentUser
                );

        return NotificationCountResponse.builder()
                .unreadCount(unreadCount)
                .build();
    }
}
