package com.flowledger.service;

import com.flowledger.dto.request.LoginRequest;
import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.LoginResponse;
import com.flowledger.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}