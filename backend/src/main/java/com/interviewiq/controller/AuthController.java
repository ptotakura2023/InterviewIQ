package com.interviewiq.controller;

import com.interviewiq.dto.AuthResponse;
import com.interviewiq.dto.LoginRequest;
import com.interviewiq.entity.User;
import com.interviewiq.service.JwtService;
import com.interviewiq.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import com.interviewiq.dto.ApiResponse;
import com.interviewiq.dto.RegisterRequest;
import com.interviewiq.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;	

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService; 

	public AuthController(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsServiceImpl userDetailsService) {
				this.userRepository = userRepository;
				this.passwordEncoder = passwordEncoder;
				this.authenticationManager = authenticationManager;
				this.jwtService = jwtService;
				this.userDetailsService = userDetailsService;
			}
	// REGISTER ENDPOINT
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
    //LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(
            @Valid @RequestBody LoginRequest request) {

        // 1. verify email and password using AuthenticationManager
        // throws BadCredentialsException automatically if wrong
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid email or password"));
        }

        // 2. load user details from database
        UserDetails userDetails = userDetailsService
            .loadUserByUsername(request.getEmail());

        // 3. generate JWT token
        String token = jwtService.generateToken(userDetails);

        // 4. get full user info to return in response
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();

        // 5. build response with token and user info
        AuthResponse authResponse = new AuthResponse(
            token,
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Login successful!", authResponse));
    }
}