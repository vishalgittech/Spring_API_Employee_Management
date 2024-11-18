package com.employee_system.emp_management.SpringSecurity;

import com.employee_system.emp_management.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;  // Utility class to handle JWT operations
    private final CustomUserDetailsService userDetailsService;  // Service for loading user details

    // Constructor to initialize JwtTokenUtil and CustomUserDetailsService
    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get the JWT token from the Authorization header of the request
        final String token = request.getHeader("Authorization");
        String username = null;  // Initialize username to null

        // Check if the token exists and starts with "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);  // Extract the token by removing "Bearer " prefix
            try {
                // Extract username (email) from the token
                username = jwtTokenUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                // If the token is expired, send a 401 Unauthorized response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token Expired or Invalid");
                return;  // Stop further processing if the token is invalid
            } catch (Exception e) {
                // Catch any other exceptions related to the token (e.g., invalid signature)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token Invalid");
                return;
            }
        }

        // If the username is found and no authentication is set in the security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database based on the extracted username
            var userDetails = userDetailsService.loadUserByUsername(username);
            // Validate the token by checking if it matches the username
            if (jwtTokenUtil.validateToken(request.getHeader("Authorization").substring(7), username)) {
                // If the token is valid, create an authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Set the authentication object in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Proceed with the filter chain, allowing the request to continue
        filterChain.doFilter(request, response);
    }
}
