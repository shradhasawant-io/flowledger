package com.flowledger.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCountResponse {

    private Long unreadCount;

}