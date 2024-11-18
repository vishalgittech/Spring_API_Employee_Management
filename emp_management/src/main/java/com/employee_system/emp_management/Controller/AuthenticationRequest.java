package com.employee_system.emp_management.Controller;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class AuthenticationRequest {

    // Getters and Setters
    private String username;  // username/email
    private String password;  // password

}
