package com.sweetshop.attendance.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sweetshop.attendance.model.Employee;
import com.sweetshop.attendance.repository.EmployeeRepository;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
     @Autowired
     public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public Employee addEmployee(Employee employee) {
        // Check if employee with same name already exists
        List<Employee> existingEmployees = employeeRepository.findByName(employee.getName());
        if (!existingEmployees.isEmpty()) {
            throw new IllegalArgumentException("Employee with this name already exists");
        }
       // Check if phone number is provided and already exists
        if (employee.getNumber() != null && !employee.getNumber().trim().isEmpty()) {
            boolean phoneExists = employeeRepository.existsByNumber(employee.getNumber());
            if (phoneExists) {
                throw new IllegalArgumentException("Phone number already registered");
            }
        }  
        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<Employee> getEmployeeByName(String name) {
        List<Employee> employees = employeeRepository.findByName(name.trim().toLowerCase());
        if (employees.isEmpty()) {
            throw new RuntimeException("Employee with name " + name + " not found");
        }
        return employees;
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    public String deleteEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "Employee Not Found";
    }
    public Employee updateEmployeeById(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));

        // Update only the fields that should be updated
        if (updatedEmployee.getName() != null) {
            existingEmployee.setName(updatedEmployee.getName());
        }
        if (updatedEmployee.getRole() != null) {
            existingEmployee.setRole(updatedEmployee.getRole());
        }
        if (updatedEmployee.getNumber() != null) {
            // Optional: Add phone number validation here
            existingEmployee.setNumber(updatedEmployee.getNumber());
        }
        if (updatedEmployee.getSalary() != null) {
            existingEmployee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepository.save(existingEmployee);
    }
}
