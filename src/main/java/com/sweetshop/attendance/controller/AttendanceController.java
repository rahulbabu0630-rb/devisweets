package com.sweetshop.attendance.controller;

import com.sweetshop.attendance.model.Attendance;
import com.sweetshop.attendance.repository.AttendanceRepository;
import com.sweetshop.attendance.service.AttendanceService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    //API to Mark Attendance
    @PostMapping("/mark")
    public String markAttendance(@RequestParam Long employeeId, @RequestParam String status) {
        return attendanceService.markAttendance(employeeId, status);
    }
    // ✅ API to Mark Attendance for Past Days
    @PostMapping("/mark-past")
    public String markPastAttendance(@RequestParam Long employeeId, 
                                     @RequestParam String status, 
                                     @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.markPastAttendance(employeeId, status, localDate);
    }

    //API to Get Month-Wise Attendance Summary
    @GetMapping("/summary")
    public String getMonthlyAttendance(@RequestParam Long employeeId, 
                                       @RequestParam int year, 
                                       @RequestParam int month) {
        return attendanceService.getMonthlyAttendance(employeeId, year, month);
    }
    
  
    // ✅ API to Get Attendance Records (Only Last Entry Per Day)
    @GetMapping("/filter")
    public List<Attendance> getLastAttendanceByMonth(@RequestParam Long employeeId,
                                                     @RequestParam int year,
                                                     @RequestParam int month) {
        return attendanceService.getLastAttendanceByMonth(employeeId, year, month);
    }
    // ✅ API to Update Past Attendance Salary Based on Status
    @PutMapping("/update-past-attendance")
    public String updatePastAttendance(@RequestParam Long employeeId, 
                                       @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Convert date string to LocalDate
        return attendanceService.updatePastAttendance(employeeId, localDate);
    }
    

}