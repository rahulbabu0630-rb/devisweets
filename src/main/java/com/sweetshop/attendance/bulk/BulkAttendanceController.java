 package com.sweetshop.attendance.bulk;

import org.springframework.beans.factory.annotation.Autowired; import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/bulk-attendance")
public class BulkAttendanceController {

@Autowired
private BulkAttendanceService bulkAttendanceService;

// ✅ API to Mark Attendance for Multiple Employees with Same Status
@PostMapping("/mark")
public String markBulkAttendance(@RequestBody BulkAttendanceRequest request) {
    return bulkAttendanceService.markBulkAttendance(request.getEmployeeIds(), request.getStatus());
}

} 