package com.sweetshop.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sweetshop.attendance.model.Employee;


import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	// ✅ Find All Employees with Partial Name Match
    @Query("SELECT e FROM Employee e WHERE LOWER(TRIM(e.name)) LIKE LOWER(CONCAT('%', TRIM(:name), '%'))")
    List<Employee> findByName(@Param("name") String name);

}