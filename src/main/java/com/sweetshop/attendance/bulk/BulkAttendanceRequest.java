package com.sweetshop.attendance.bulk;

import java.util.List;

public class BulkAttendanceRequest { private List<Long> employeeIds; private String status;

// ✅ Constructor
public BulkAttendanceRequest() {
}

public BulkAttendanceRequest(List<Long> employeeIds, String status) {
    this.employeeIds = employeeIds;
    this.status = status;
}

// ✅ Getter for employeeIds
public List<Long> getEmployeeIds() {
    return employeeIds;
}

// ✅ Setter for employeeIds
public void setEmployeeIds(List<Long> employeeIds) {
    this.employeeIds = employeeIds;
}

// ✅ Getter for status
public String getStatus() {
    return status;
}

// ✅ Setter for status
public void setStatus(String status) {
    this.status = status;
}

}   