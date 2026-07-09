package com.flowledger.mapper;

import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.UserResponse;
import com.flowledger.entity.User;
import com.flowledger.enums.Currency;
import com.flowledger.enums.UserRole;

public class UserMapper {

    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setRole(UserRole.USER);
        user.setCurrency(Currency.INR);

        user.setTimezone(request.getTimezone());
        user.setEnabled(true);

        return user;
    }

    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .currency(user.getCurrency())
                .role(user.getRole())
                .timezone(user.getTimezone())
                .enabled(user.getEnabled())
                .build();
    }
}