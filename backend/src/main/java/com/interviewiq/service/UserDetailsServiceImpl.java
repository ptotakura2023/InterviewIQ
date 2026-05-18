package com.interviewiq.service;

import com.interviewiq.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security calls this when someone tries to log in
    // "username" here is actually the email in our case
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // find user in DB by email
        com.interviewiq.entity.User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User not found with email: " + email
            ));

        // convert our Role enum to Spring Security's GrantedAuthority
        // Spring Security expects roles prefixed with "ROLE_"
        // so CANDIDATE becomes ROLE_CANDIDATE
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
            "ROLE_" + user.getRole().name()
        );

        // return Spring Security's User object with email, password, and role
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(authority)
        );
    }
}