package com.sweetshop.attendance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sweetshop.attendance.model.Employee;
import com.sweetshop.attendance.service.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = {
    "https://durgadevisweets.vercel.app",
    "https://devisweets1.vercel.app",
    "http://localhost:5173",
    "https://newrepo-rose.vercel.app"
}, allowCredentials = "true")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/add")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get")
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeService.getEmployeeByName(name);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployeeByName(@PathVariable Long id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        try {
            Employee updated = employeeService.updateEmployeeById(id, updatedEmployee);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    } 
}