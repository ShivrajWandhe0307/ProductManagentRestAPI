package com.example.ProductManagementRestAPI.Controller;

import com.example.ProductManagementRestAPI.Configuration.AuthResponse;
import com.example.ProductManagementRestAPI.DTO.UserDTO;
import com.example.ProductManagementRestAPI.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> newUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Received request to create new user with email: {}", userDTO.getEmail());
        try {
            UserDTO user = userService.newUser(userDTO);
            log.info("User created successfully with ID: {}", user.getId());
            log.debug("Created User details: {}", user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating user with email {}: {}", userDTO.getEmail(), e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        log.info("Login attempt for email: {}", email);
        try {
            AuthResponse response = userService.loginUser(email, password);
            log.info("Login successful for email: {}", email);
            log.debug("AuthResponse: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.warn("Login failed for email {}: {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}