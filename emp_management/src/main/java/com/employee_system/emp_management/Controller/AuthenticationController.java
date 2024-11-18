package com.employee_system.emp_management.Controller;

import com.employee_system.emp_management.Service.CustomUserDetailsService;
import com.employee_system.emp_management.SpringSecurity.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService userDetailsService;  // Inject CustomUserDetailsService

    @Autowired
    private AuthenticationManager authenticationManager;   // AuthenticationManager

    @Autowired
    private JwtTokenUtil jwtTokenUtil;  // JWT Utility Service

    @PostMapping
    public String createToken(@RequestBody AuthenticationRequest request) {
        // Authenticate the user by username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Load the user details from the database (through CustomUserDetailsService)
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate and return the JWT token after successful authentication
        return jwtTokenUtil.generateToken(userDetails.getUsername());
    }
}
