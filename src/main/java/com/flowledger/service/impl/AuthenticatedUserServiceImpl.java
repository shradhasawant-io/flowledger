package com.flowledger.service.impl;

import com.flowledger.entity.User;
import com.flowledger.exception.ResourceNotFoundException;
import com.flowledger.repository.UserRepository;
import com.flowledger.service.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticatedUserServiceImpl
        implements AuthenticatedUserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Authenticated user not found"));
    }
}