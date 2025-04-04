package com.sweetshop.attendance.util;

import java.time.YearMonth;
import java.time.LocalDate;

public class DateUtils {
    // ✅ Utility function to get the actual number of days in a month
    public static int getDaysInMonth(LocalDate date) {
        return YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth();
    }
}