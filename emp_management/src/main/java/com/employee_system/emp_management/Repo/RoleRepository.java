package com.employee_system.emp_management.Repo;


import com.employee_system.emp_management.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}