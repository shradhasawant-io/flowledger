package com.flowledger.controller;

import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.FinancialStreakResponse;
import com.flowledger.service.FinancialStreakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/streaks")
@RequiredArgsConstructor
@Tag(
        name = "Financial Streaks",
        description = "Financial streak analytics APIs"
)
@SecurityRequirement(name = "bearerAuth")
public class FinancialStreakController {

    private final FinancialStreakService financialStreakService;

    @GetMapping
    @Operation(summary = "Get financial streaks")
    public ResponseEntity<ApiResponse<FinancialStreakResponse>>
    getFinancialStreaks() {

        FinancialStreakResponse response =
                financialStreakService.getFinancialStreaks();

        return ResponseEntity.ok(
                ApiResponse.<FinancialStreakResponse>builder()
                        .success(true)
                        .message("Financial streaks retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

}