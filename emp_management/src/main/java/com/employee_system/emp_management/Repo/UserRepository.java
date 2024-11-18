package com.employee_system.emp_management.Repo;

import com.employee_system.emp_management.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String >
{
    // Custom query method to find a user by email
    Optional<User> findByEmail(String email);
}
