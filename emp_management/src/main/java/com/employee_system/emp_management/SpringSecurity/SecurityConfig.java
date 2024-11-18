package com.employee_system.emp_management.SpringSecurity;

import com.employee_system.emp_management.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;  // Service to load user details
    private final JwtRequestFilter jwtRequestFilter;  // Custom filter for JWT authentication

    // Constructor to initialize userDetailsService and jwtRequestFilter
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Configure HTTP security for the application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disable CSRF protection (can be enabled for production)
                .authorizeRequests(authz -> authz
                        .requestMatchers("/authenticate").permitAll()  // Allow unauthenticated access to the /authenticate endpoint
                        .anyRequest().authenticated())  // Require authentication for all other requests
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before default authentication filter

        return http.build();  // Build and return the security configuration
    }

    // Configure AuthenticationManager for user authentication
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)  // Set the custom user details service to load user info
                .passwordEncoder(new BCryptPasswordEncoder());  // Set the password encoder for hashing passwords
        return authenticationManagerBuilder.build();  // Return the configured AuthenticationManager
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Password encoder for BCrypt
    }
}
