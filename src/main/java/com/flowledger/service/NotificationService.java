package com.flowledger.service;

import com.flowledger.dto.response.NotificationCountResponse;
import com.flowledger.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications();

    NotificationResponse markAsRead(Long notificationId);

    void markAllAsRead();

    NotificationCountResponse getUnreadCount();
}
