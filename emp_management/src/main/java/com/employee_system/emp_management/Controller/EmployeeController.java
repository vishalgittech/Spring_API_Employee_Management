package com.employee_system.emp_management.Controller;

import com.employee_system.emp_management.Entities.Employee;
import com.employee_system.emp_management.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")  // Base URL mapping for all employee-related API endpoints
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;  // Service to handle employee-related business logic

    // Endpoint to add a new employee
    @PostMapping
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee, @RequestHeader("Authorization") String token) {
        // Token validation is handled by JwtRequestFilter, so we only proceed if the token is valid
        Employee createdEmployee = employeeService.addEmployee(employee);  // Add employee using the service
        if (createdEmployee != null) {
            // Return success response if the employee is created successfully
            return new ResponseEntity<>("Employee Added", HttpStatus.CREATED);
        } else {
            // Return error response if the employee could not be added
            return new ResponseEntity<>("Error adding employee", HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to get an employee by their ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);  // Fetch employee by ID from the service
        if (employee != null) {
            // Return employee details if found
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            // Return not found status if the employee does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
