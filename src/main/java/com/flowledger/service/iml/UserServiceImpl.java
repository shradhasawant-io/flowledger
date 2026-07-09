package com.flowledger.service.iml;

import com.flowledger.dto.request.RegisterRequest;
import com.flowledger.dto.response.UserResponse;
import com.flowledger.entity.User;
import com.flowledger.exception.EmailAlreadyExistsException;
import com.flowledger.mapper.UserMapper;
import com.flowledger.repository.UserRepository;
import com.flowledger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}