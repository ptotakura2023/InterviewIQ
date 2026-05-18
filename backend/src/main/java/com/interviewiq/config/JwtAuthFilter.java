package com.interviewiq.config;

import com.interviewiq.service.JwtService;
import com.interviewiq.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // get the Authorization header from the request
        // it should look like: "Bearer eyJhbGci..."
        final String authHeader = request.getHeader("Authorization");

        // if no auth header or doesn't start with Bearer, skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // extract just the token part (remove "Bearer " prefix)
        final String jwt = authHeader.substring(7);

        // extract email from token
        final String email = jwtService.extractEmail(jwt);

        // if we got an email and user isn't already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // load user from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // check if token is valid
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // create authentication object and set it in Spring Security context
                // this tells Spring Security — "this user is authenticated"
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // continue to the next filter
        filterChain.doFilter(request, response);
    }
}