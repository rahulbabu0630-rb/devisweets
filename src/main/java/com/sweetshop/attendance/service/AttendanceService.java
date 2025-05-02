package com.sweetshop.attendance.service;

import com.sweetshop.attendance.model.Attendance;
import com.sweetshop.attendance.model.Employee;
import com.sweetshop.attendance.repository.AttendanceRepository;
import com.sweetshop.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Mark Attendance (Allows updating existing entry for the same day)
    public String markAttendance(Long employeeId, String status) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return "Employee not found!";
        }

        Employee employee = employeeOpt.get();
        LocalDate today = LocalDate.now();

        int daysInMonth = YearMonth.of(today.getYear(), today.getMonthValue()).lengthOfMonth();
        	double dailySalary = employee.getSalary() / daysInMonth ;
        
        // ✅ Validate status before processing
        if (!status.equalsIgnoreCase("present") &&
            !status.equalsIgnoreCase("halfday") &&
            !status.equalsIgnoreCase("absent")) {
            return "Invalid status! Allowed values: present, halfday, absent";
        }

        // ✅ Salary Calculation
        double salaryForTheDay = 0.0;
        switch (status.toLowerCase()) {
            case "present" -> salaryForTheDay = dailySalary;
            case "halfday" -> salaryForTheDay = dailySalary / 2;
            case "absent" -> salaryForTheDay = 0.0;
        }

        // ✅ Check if attendance exists for today
        Optional<Attendance> existingAttendanceOpt = attendanceRepository.findTopByEmployee_NameAndDateOrderByIdDesc(employee.getName(), today);

        if (existingAttendanceOpt.isPresent()) {
            // ✅ Update existing attendance
            Attendance existingAttendance = existingAttendanceOpt.get();
            existingAttendance.setStatus(status);
            existingAttendance.setSalary(salaryForTheDay);
            attendanceRepository.save(existingAttendance);
            return "Attendance updated successfully!";
        }

        // ✅ Save new attendance entry
        Attendance attendance = new Attendance(employee, today, status, salaryForTheDay);
        attendanceRepository.save(attendance);

        return "Attendance marked successfully!";
    }

    // ✅ Get Monthly Attendance Summary
    public String getMonthlyAttendance(Long employeeId, int year, int month) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return "Employee not found!";
        }

        Employee employee = employeeOpt.get();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // ✅ Fetch attendance records for the given month
        List<Attendance> attendanceList = attendanceRepository.findLastAttendanceByMonth(employeeId, startDate, endDate);

        // ✅ Initialize counters
        int totalPresent = 0, totalAbsent = 0, totalHalfDay = 0;
        double totalSalary = 0;

        // ✅ Calculate attendance summary
        for (Attendance attendance : attendanceList) {
            switch (attendance.getStatus().toLowerCase()) {
                case "present" -> totalPresent++;
                case "halfday" -> totalHalfDay++;
                case "absent" -> totalAbsent++;
            }
            totalSalary += attendance.getSalary();
        }

        return String.format(
            "Monthly Attendance Summary for %s (%s)\nTotal Present Days: %d\nTotal Half-Days: %d\nTotal Absent Days: %d\nTotal Salary Earned: ₹%.2f",
            employee.getName(), yearMonth, totalPresent, totalHalfDay, totalAbsent, totalSalary
        );
    }

    // ✅ Get Last Attendance Entry Per Day (Removes Duplicates)
    public List<Attendance> getLastAttendanceByMonth(Long employeeId, int year, int month) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found!");
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        return attendanceRepository.findLastAttendanceByMonth(employeeId, startDate, endDate);

    }
    public String updatePastAttendance(Long employeeId, LocalDate date) {
        Optional<Attendance> attendanceOpt = attendanceRepository.findTopByEmployee_IdAndDateOrderByIdDesc(employeeId, date);

        if (attendanceOpt.isPresent()) {
            Attendance attendance = attendanceOpt.get();
            int daysInMonth = YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth();

            double correctSalary;

            // ✅ Calculate salary based on attendance status
            switch (attendance.getStatus().toLowerCase()) {
                case "present":
                    correctSalary = attendance.getEmployee().getSalary() / daysInMonth;
                    break;
                case "halfday":
                    correctSalary = (attendance.getEmployee().getSalary() / daysInMonth) / 2;
                    break;
                case "absent":
                    correctSalary = 0;
                    break;
                default:
                    return "Invalid status found!";
            }

            attendance.setSalary(correctSalary); // ✅ Update salary
            attendanceRepository.save(attendance);
            return "Attendance updated successfully for " + attendance.getEmployee().getName() + " on " + date;
        }
        return "No attendance record found for the given date.";
    }
    // ✅ Mark Attendance for Past Dates
    public String markPastAttendance(Long employeeId, String status, LocalDate date) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return "Employee not found!";
        }

        Employee employee = employeeOpt.get();
        int daysInMonth = YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth();
        double dailySalary = employee.getSalary() / daysInMonth ; // Assuming a 30-day month

        // ✅ Validate status before processing
        if (!status.equalsIgnoreCase("present") &&
            !status.equalsIgnoreCase("halfday") &&
            !status.equalsIgnoreCase("absent")) {
            return "Invalid status! Allowed values: present, halfday, absent";
        }

        // ✅ Salary Calculation
        double salaryForTheDay = switch (status.toLowerCase()) {
            case "present" -> dailySalary;
            case "halfday" -> dailySalary / 2;
            default -> 0.0;
        };

        // ✅ Check if attendance exists for the given past date
        Optional<Attendance> existingAttendanceOpt = attendanceRepository.findTopByEmployee_IdAndDateOrderByIdDesc(employeeId, date);

        if (existingAttendanceOpt.isPresent()) {
            // ✅ Update existing attendance
            Attendance existingAttendance = existingAttendanceOpt.get();
            existingAttendance.setStatus(status);
            existingAttendance.setSalary(salaryForTheDay);
            attendanceRepository.save(existingAttendance);
            return "Past attendance updated successfully for " + date;
        }

        // ✅ Save new attendance entry
        Attendance attendance = new Attendance(employee, date, status, salaryForTheDay);
        attendanceRepository.save(attendance);

        return "Past attendance marked successfully for " + date;
    }
 // ✅ Get Today's Attendance Status with Employee Details
    public Map<String, Object> getTodayAttendanceStatus(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found!");
        }

        Employee employee = employeeOpt.get();
        LocalDate today = LocalDate.now();
        
        // Find today's attendance if marked
        Optional<Attendance> todayAttendanceOpt = attendanceRepository
                .findTopByEmployee_IdAndDateOrderByIdDesc(employeeId, today);

        String status = "Not Marked";
        if (todayAttendanceOpt.isPresent()) {
            status = todayAttendanceOpt.get().getStatus();
        }

        // Prepare response map
        return Map.of(
            "employeeName", employee.getName(),
            "currentDate", today.toString(),
            "attendanceStatus", status
        );
    }
    public List<Map<String, Object>> getAllEmployeesTodayStatus() {
        LocalDate today = LocalDate.now();
        
        // Get all employees (since isActive field isn't shown in your model)
        List<Employee> allEmployees = employeeRepository.findAll();
        
        // Get today's attendance records
        List<Attendance> todaysAttendances = attendanceRepository.findByDate(today);
        
        // Create a map for quick lookup of employee attendance
        Map<Long, Attendance> employeeAttendanceMap = todaysAttendances.stream()
            .collect(Collectors.toMap(
                att -> att.getEmployee().getId(),
                att -> att,
                (existing, replacement) -> existing // Keep first entry if duplicates
            ));
        
        // Build the response
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Employee employee : allEmployees) {
            Map<String, Object> employeeStatus = new LinkedHashMap<>();
            employeeStatus.put("employeeId", employee.getId());
            employeeStatus.put("employeeName", employee.getName());
            employeeStatus.put("role", employee.getRole());
            employeeStatus.put("salary", employee.getSalary());
            
            // Get attendance status (default to "absent" if no record)
            Attendance attendance = employeeAttendanceMap.get(employee.getId());
            String status = (attendance != null) ? attendance.getStatus().toLowerCase() : "absent";
            employeeStatus.put("status", status);
            employeeStatus.put("date", today.toString());
            
            result.add(employeeStatus);
        }
        
        return result;
    }
}