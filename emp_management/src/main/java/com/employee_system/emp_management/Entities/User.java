package com.employee_system.emp_management.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    private String email;  // Primary key

    private String password;
    private String role;   // Role, e.g., ROLE_USER or ROLE_ADMIN
}
