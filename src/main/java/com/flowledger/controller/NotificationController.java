package com.flowledger.controller;


import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.NotificationCountResponse;
import com.flowledger.dto.response.NotificationResponse;
import com.flowledger.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(
        name = "Notifications",
        description = "Notification Center APIs"
)
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getNotifications() {

        List<NotificationResponse> response =
                notificationService.getNotifications();

        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .success(true)
                        .message("Notifications retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<ApiResponse<NotificationResponse>>
    markAsRead(
            @PathVariable Long notificationId) {

        NotificationResponse response =
                notificationService.markAsRead(notificationId);

        return ResponseEntity.ok(
                ApiResponse.<NotificationResponse>builder()
                        .success(true)
                        .message("Notification marked as read.")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/read-all")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<ApiResponse<Void>>
    markAllAsRead() {

        notificationService.markAllAsRead();

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("All notifications marked as read.")
                        .build()
        );
    }

    @GetMapping("/unread-count")
    @Operation(summary = "Get unread notification count")
    public ResponseEntity<ApiResponse<NotificationCountResponse>>
    getUnreadCount() {

        NotificationCountResponse response =
                notificationService.getUnreadCount();

        return ResponseEntity.ok(
                ApiResponse.<NotificationCountResponse>builder()
                        .success(true)
                        .message("Unread notification count retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

}