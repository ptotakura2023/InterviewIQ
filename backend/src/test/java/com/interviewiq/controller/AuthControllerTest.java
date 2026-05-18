package com.interviewiq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewiq.dto.RegisterRequest;
import com.interviewiq.entity.Role;
import com.interviewiq.entity.User;
import com.interviewiq.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* @WebMvcTest loads ONLY the web layer (controllers)
   It does NOT load the full Spring context or hit the database
   Think of it as testing just the "front door" of your application*/
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    // MockMvc simulates HTTP requests — like Postman but in code
    // No real server starts — it's all in memory
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper converts Java objects to JSON strings
    private ObjectMapper objectMapper = new ObjectMapper();

    // @MockBean replaces the real UserRepository with a fake one
    // We control exactly what it returns in each test
    @MockitoBean
    private UserRepository userRepository;

    // @MockBean replaces the real BCrypt encoder with a fake one
    @MockitoBean
    private PasswordEncoder passwordEncoder;

    // This is the valid request we'll reuse across tests
    private RegisterRequest validRequest;

    // @BeforeEach runs before every single test
    // Sets up fresh test data so tests don't affect each other
    @BeforeEach
    void setUp() {
        validRequest = new RegisterRequest();
        validRequest.setName("Pranay");
        validRequest.setEmail("pranay@example.com");
        validRequest.setPassword("securePassword123");
        validRequest.setRole(Role.CANDIDATE);
    }

    // TEST 1: Successful Registration
    @Test
    @DisplayName("Should register user successfully with valid data")
    @WithMockUser  // Simulates an authenticated user passing Spring Security
    void shouldRegisterUserSuccessfully() throws Exception {

        // ARRANGE — set up what the mocks should return
        // "When someone asks if this email exists, return false (it doesn't)"
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // When someone asks to encode a password, return this fake hash
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hashedPassword");

        // "When someone asks to save a user, return a fake saved user"
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Pranay");
        savedUser.setEmail("pranay@example.com");
        savedUser.setRole(Role.CANDIDATE);
        savedUser.setPassword(null); // password cleared before response
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // send request and verify response
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())  // Required by Spring Security for POST requests
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isCreated())                          // 201
            .andExpect(jsonPath("$.success").value(true))             // success: true
            .andExpect(jsonPath("$.message").value("User registered successfully!"))
            .andExpect(jsonPath("$.data.email").value("pranay@example.com"))
            .andExpect(jsonPath("$.data.password").doesNotExist());   // password not exposed
    }

    // TEST 2: Duplicate Email 
    @Test
    @DisplayName("Should return 400 when email is already taken")
    @WithMockUser
    void shouldReturn400WhenEmailAlreadyTaken() throws Exception {

        // ARRANGE — email already exists in database
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // ACT + ASSERT
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())                       // 400
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Email is already taken"));
    }

    // ─── TEST 3: Empty Name ──────────────────────────────────────────────
    @Test
    @DisplayName("Should return 400 when name is blank")
    @WithMockUser
    void shouldReturn400WhenNameIsBlank() throws Exception {

        // ARRANGE — set name to empty
        validRequest.setName("");

        // ACT + ASSERT
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())                       // 400
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.data.name").value("Name is required"));
    }

    // ─── TEST 4: Invalid Email Format ────────────────────────────────────
    @Test
    @DisplayName("Should return 400 when email format is invalid")
    @WithMockUser
    void shouldReturn400WhenEmailIsInvalid() throws Exception {

        // ARRANGE — set invalid email
        validRequest.setEmail("not-a-valid-email");

        // ACT + ASSERT
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.data.email").value("Email should be valid"));
    }

    // ─── TEST 5: Short Password ───────────────────────────────────────────
    @Test
    @DisplayName("Should return 400 when password is less than 6 characters")
    @WithMockUser
    void shouldReturn400WhenPasswordIsTooShort() throws Exception {

        // ARRANGE — set short password
        validRequest.setPassword("Ab1");

        // ACT + ASSERT
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.data.password")
                .value("Password must be at least 6 characters"));
    }
    //TEST 6: Password Pattern Validation
    @Test
    @DisplayName("Should return 400 when password has no uppercase, lowercase or number")
    @WithMockUser
    void shouldReturn400WhenPasswordFailsComplexity() throws Exception {

        // This password is long enough but fails @Pattern
        validRequest.setPassword("alllowercase");

        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.data.password")
                .value("Password must contain at least one uppercase letter, one lowercase letter, and one number"));
    }
}
