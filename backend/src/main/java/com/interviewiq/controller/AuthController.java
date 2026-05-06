package com.interviewiq.controller;

import com.interviewiq.dto.ApiResponse;
import com.interviewiq.dto.RegisterRequest;
import com.interviewiq.entity.User;
import com.interviewiq.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;	

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // For React integration later
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody RegisterRequest request) {
        
        // 1. Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Email is already taken"));
        }

        // 2. Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        
        // encoded password with BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3. Save to database
        User savedUser = userRepository.save(user);

        // 4. Don't send password back to frontend (security!)
        savedUser.setPassword(null);

        // 5. Return success response with user data
        return ResponseEntity
            .status(HttpStatus.CREATED)  // 201 Created
            .body(ApiResponse.success("User registered successfully!", savedUser));
    }
}