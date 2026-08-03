package com.flowledger.controller;

import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.BillReminderResponse;
import com.flowledger.service.BillReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
@RequiredArgsConstructor
@Tag(
        name = "Bill Reminders",
        description = "Bill Reminder Management APIs"
)
@SecurityRequirement(name = "bearerAuth")
public class BillReminderController {

    private final BillReminderService billReminderService;

    @GetMapping
    @Operation(summary = "Get pending bill reminders")
    public ResponseEntity<ApiResponse<List<BillReminderResponse>>>
    getPendingReminders() {

        List<BillReminderResponse> response =
                billReminderService.getPendingReminders();

        return ResponseEntity.ok(
                ApiResponse.<List<BillReminderResponse>>builder()
                        .success(true)
                        .message("Bill reminders retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{reminderId}/dismiss")
    @Operation(summary = "Dismiss bill reminder")
    public ResponseEntity<ApiResponse<BillReminderResponse>>
    dismissReminder(
            @PathVariable Long reminderId) {

        BillReminderResponse response =
                billReminderService.dismissReminder(reminderId);

        return ResponseEntity.ok(
                ApiResponse.<BillReminderResponse>builder()
                        .success(true)
                        .message("Bill reminder dismissed successfully.")
                        .data(response)
                        .build()
        );
    }

}
