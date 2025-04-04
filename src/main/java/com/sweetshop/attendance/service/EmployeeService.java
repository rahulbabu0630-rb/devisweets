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
        List<Employee> existingEmployees = employeeRepository.findByName(employee.getName());
        if (!existingEmployees.isEmpty()) {
            throw new IllegalArgumentException("Employee already exists");
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

    public String updateEmployeeById(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setRole(updatedEmployee.getRole());
        existingEmployee.setNumber(updatedEmployee.getNumber());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        employeeRepository.save(existingEmployee);
        return "Employee updated successfully.";
    }
}
