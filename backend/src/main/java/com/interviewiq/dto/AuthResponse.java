package com.interviewiq.dto;

import com.interviewiq.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse{

    // JWT token — frontend stores this and sends it with every request
    private String token;

    // basic user info — frontend uses this to show name, role etc.
    private Long id;
    private String name;
    private String email;
    private Role role;
}