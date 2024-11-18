package com.employee_system.emp_management.Service;

import com.employee_system.emp_management.Entities.Department;
import com.employee_system.emp_management.Entities.Employee;
import com.employee_system.emp_management.Entities.Role;
import com.employee_system.emp_management.Repo.DepartmentRepository;
import com.employee_system.emp_management.Repo.EmployeeRepo;
import com.employee_system.emp_management.Repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;  // Repository for employee data

    @Autowired
    private DepartmentRepository departmentRepository;  // Repository for department data

    @Autowired
    private RoleRepository roleRepository;  // Repository for role data

    // Method to add a new employee
    public Employee addEmployee(Employee employee) {
        // Fetch the department using the department ID provided in the employee object
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));  // Throw an error if the department is not found

        // Fetch the role using the role ID provided in the employee object
        Role role = roleRepository.findById(employee.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));  // Throw an error if the role is not found

        // Set the department and role to the employee object
        employee.setDepartment(department);
        employee.setRole(role);

        // Save the employee object in the repository and return the saved employee
        return employeeRepository.save(employee);
    }

    // Method to get an employee by their ID
    public Employee getEmployeeById(Long id) {
        // Fetch the employee by ID, return null if the employee is not found
        return employeeRepository.findById(id).orElse(null);
    }
}
