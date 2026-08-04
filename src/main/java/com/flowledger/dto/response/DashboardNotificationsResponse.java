package com.flowledger.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardNotificationsResponse {

    private Long unreadCount;

    private List<DashboardNotificationResponse> notifications;

}