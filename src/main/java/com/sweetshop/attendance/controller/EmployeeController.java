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
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/add")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/all")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/get")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeService.getEmployeeByName(name);
    }

    @GetMapping("/getById/{id}")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public String deleteEmployeeByName(@PathVariable Long id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = {"https://durgadevisweets.vercel.app", "http://localhost:5173"})
    public String updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployeeById(id, updatedEmployee);
    } 
}