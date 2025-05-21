package com.sweetshop.attendance.bulk;
import com.sweetshop.attendance.model.Attendance;
import com.sweetshop.attendance.model.Employee;
import com.sweetshop.attendance.repository.AttendanceRepository;
import com.sweetshop.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class BulkAttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Bulk Mark Attendance with Same Status for Multiple Employees
    public String markBulkAttendance(List<Long> employeeIds, String status) {
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonthValue());
        int daysInMonth = yearMonth.lengthOfMonth(); // Get correct number of days in the month
        List<Attendance> attendanceList = new ArrayList<>();

        // Validate status
        if (!status.equalsIgnoreCase("present") &&
            !status.equalsIgnoreCase("halfday") &&
            !status.equalsIgnoreCase("absent")) {
            return "Invalid status! Allowed values: present, halfday, absent";
        }

        for (Long employeeId : employeeIds) {
            Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
            if (employeeOpt.isEmpty()) {
                return "Employee with ID " + employeeId + " not found!";
            }

            Employee employee = employeeOpt.get();

            double dailySalary = employee.getSalary() / daysInMonth; // Assuming a 30-day month
            double salaryForTheDay = 0;

            // Calculate salary based on status
            switch (status.toLowerCase()) {
                case "present":
                    salaryForTheDay = dailySalary;
                    break;
                case "halfday":
                    salaryForTheDay = dailySalary / 2;
                    break;
                case "absent":
                    salaryForTheDay = 0;
                    break;
            }

            // Find attendance by employeeId and date
            List<Attendance> existingAttendances = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);

            if (!existingAttendances.isEmpty()) {
                // If records exist, update all of them
                for (Attendance existing : existingAttendances) {
                    existing.setStatus(status);
                    existing.setSalary(salaryForTheDay);
                    attendanceList.add(existing);
                }
            } else {
                // If no records exist, create a new one
                Attendance newAttendance = new Attendance(employee, today, status, salaryForTheDay);
                attendanceList.add(newAttendance);
            }
        }

        // Save all attendance records in a single transaction
        attendanceRepository.saveAll(attendanceList);

        return "Attendance marked successfully for selected employees!";
    }
}
