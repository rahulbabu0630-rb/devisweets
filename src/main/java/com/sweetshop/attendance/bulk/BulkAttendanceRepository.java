package com.sweetshop.attendance.bulk;
import com.sweetshop.attendance.model.Attendance; import org.springframework.data.jpa.repository.JpaRepository; import org.springframework.stereotype.Repository;

@Repository public interface BulkAttendanceRepository extends JpaRepository<Attendance, Long> {
}
