package com.flowledger.controller;

import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.DashboardSummaryResponse;
import com.flowledger.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary() {

        DashboardSummaryResponse summary =
                dashboardService.getDashboardSummary();

        ApiResponse<DashboardSummaryResponse> response =
                ApiResponse.<DashboardSummaryResponse>builder()
                        .success(true)
                        .message("Dashboard summary retrieved successfully")
                        .data(summary)
                        .build();

        return ResponseEntity.ok(response);
    }
}