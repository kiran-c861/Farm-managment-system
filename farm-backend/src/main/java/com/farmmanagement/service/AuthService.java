package com.farmmanagement.service;

import com.farmmanagement.dto.AuthResponse;
import com.farmmanagement.dto.LoginRequest;
import com.farmmanagement.dto.RegisterRequest;
import com.farmmanagement.exception.BadRequestException;
import com.farmmanagement.model.User;
import com.farmmanagement.model.enums.Role;
import com.farmmanagement.repository.UserRepository;
import com.farmmanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service handling user registration, login, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user with BCrypt-encrypted password.
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered: " + request.getEmail());
        }

        Role role = request.getRole() != null ? request.getRole() : Role.WORKER;

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .role(role)
                .isActive(true)
                .build();

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getUsername(), saved.getRole().name());
        return new AuthResponse(token, saved.getUsername(), saved.getRole().name(), saved.getFullName(), saved.getId());
    }

    /**
     * Authenticate user and return JWT token.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name(), user.getFullName(), user.getId());
    }
}
