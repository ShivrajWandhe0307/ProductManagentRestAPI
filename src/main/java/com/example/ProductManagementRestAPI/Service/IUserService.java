package com.example.ProductManagementRestAPI.Service;

import com.example.ProductManagementRestAPI.Configuration.AuthResponse;
import com.example.ProductManagementRestAPI.Configuration.JwtUtils;
import com.example.ProductManagementRestAPI.DTO.UserDTO;
import com.example.ProductManagementRestAPI.Entity.User;
import com.example.ProductManagementRestAPI.Exception.UserNameAndPasswordInvalid;
import com.example.ProductManagementRestAPI.Exception.UserNotFound;
import com.example.ProductManagementRestAPI.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public UserDTO newUser(UserDTO userDTO) {
        log.info("Attempting to register new user with email: {}", userDTO.getEmail());

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.warn("Registration failed: User with email {} already exists", userDTO.getEmail());
            throw new RuntimeException("User Already Exist");
        }

        User user = User.builder()
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .role("USER")
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully with ID: {}", user.getId());
        log.debug("Registered user details: {}", user);

        return UserDTO.fromEntity(user);
    }

    @Override
    public AuthResponse loginUser(String email, String password) {
        log.info("Login attempt for user with email: {}", email);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (UserNameAndPasswordInvalid ex) {
            log.warn("Login failed for email {}: {}", email, ex.getMessage());
            throw new UserNameAndPasswordInvalid("Username and Password Invalid");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Login failed: User with email {} not found", email);
                    return new UserNotFound("User with Email: " + email + " Not Found");
                });

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        log.info("Login successful for email: {}", email);
        log.debug("Generated AccessToken: {}, RefreshToken: {}", accessToken, refreshToken);

        return new AuthResponse(
                user.getFullName(),
                accessToken,
                refreshToken
        );
    }
}