package com.flowledger.controller;

import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.ApiResponse;
import com.flowledger.dto.response.UserResponse;
import com.flowledger.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<UserResponse>builder()
                                .success(true)
                                .message("User registered successfully")
                                .data(response)
                                .build()
                );
    }
}