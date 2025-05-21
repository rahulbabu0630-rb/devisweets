package com.sweetshop.attendance.bulk;
import java.util.List;

public class BulkAttendanceRequest { private List<Long> employeeIds; private String status;
                                    
public BulkAttendanceRequest() {
}
public BulkAttendanceRequest(List<Long> employeeIds, String status) {
    this.employeeIds = employeeIds;
    this.status = status;
}

public List<Long> getEmployeeIds() {
    return employeeIds;
}

public void setEmployeeIds(List<Long> employeeIds) {
    this.employeeIds = employeeIds;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
    }
}   
