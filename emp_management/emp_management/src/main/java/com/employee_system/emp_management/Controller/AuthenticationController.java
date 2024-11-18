package com.employee_system.emp_management.Controller;

import com.employee_system.emp_management.SpringSecurity.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public String createToken(@RequestBody AuthenticationRequest request) {
        // Authenticate the user based on email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Generate and return the JWT token
        return jwtTokenUtil.generateToken(request.getUsername());
    }
}
