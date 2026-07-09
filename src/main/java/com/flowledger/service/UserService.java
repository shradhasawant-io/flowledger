package com.flowledger.service;

import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

}