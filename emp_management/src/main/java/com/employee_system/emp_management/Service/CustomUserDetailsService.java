package com.employee_system.emp_management.Service;

import com.employee_system.emp_management.Entities.User;
import com.employee_system.emp_management.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the username matches predefined user1

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
            // Return a user with the email, a plaintext password (for demonstration), and user role
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),  // Username (email)
                    user.getPassword(),  // Password
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));// User role
        }
    }
        // Check if the username matches predefined admin (For Testing purposes)
//        else if ("admin@example.com".equals(username)) {
//            // Return a user with the email, a plaintext password (for demonstration), and admin role
//            return new org.springframework.security.core.userdetails.User(
//                    "admin@example.com",  // Username (email)
//                    "{noop}admin123",  // Password (no encoding)
//                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))  // Admin role
//            );
//        } else {
            // Throw exception if the user is not found
//            throw new UsernameNotFoundException("User not found");




