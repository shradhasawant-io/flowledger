package com.flowledger.service.impl;

import com.flowledger.dto.request.LoginRequest;
import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.LoginResponse;
import com.flowledger.dto.response.UserResponse;
import com.flowledger.entity.User;
import com.flowledger.exception.EmailAlreadyExistsException;
import com.flowledger.mapper.UserMapper;
import com.flowledger.repository.UserRepository;
import com.flowledger.security.jwt.JwtService;
import com.flowledger.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}