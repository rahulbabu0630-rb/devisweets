package com.sweetshop.attendance.repository;

import com.sweetshop.attendance.model.Attendance;
import com.sweetshop.attendance.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	
	 // ✅ Fetch the most recent attendance entry for a specific employee on a given date
    Optional<Attendance> findTopByEmployee_IdAndDateOrderByIdDesc(Long employeeId, LocalDate date);


    // Retrieve attendance for a specific employee on a given date
    Attendance findByEmployee_IdAndDate(Long employeeId, LocalDate date);

    // Get all attendance records for an employee
    List<Attendance> findByEmployee_Id(Long employeeId);

    
 // ✅ Get last attendance entry per day
    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.date BETWEEN :startDate AND :endDate " +
           "AND a.id = (SELECT MAX(a2.id) FROM Attendance a2 WHERE a2.employee.id = a.employee.id AND a2.date = a.date)")
    List<Attendance> findLastAttendanceByMonth(Long employeeId, LocalDate startDate, LocalDate endDate);


    // ✅ Find Latest Attendance Entry for an Employee on a Specific Date
    Optional<Attendance> findTopByEmployee_NameAndDateOrderByIdDesc(String employeeName, LocalDate date);
    
    List<Attendance> findByEmployee_NameAndDateBetween(String name, LocalDate startDate, LocalDate endDate);






	List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate today);



}