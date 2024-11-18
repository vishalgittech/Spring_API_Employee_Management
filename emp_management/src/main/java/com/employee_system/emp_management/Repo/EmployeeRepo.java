package com.employee_system.emp_management.Repo;

import com.employee_system.emp_management.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> { ;
}
