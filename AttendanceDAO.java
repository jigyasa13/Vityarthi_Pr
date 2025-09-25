package com.sis.dao;

import com.sis.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDAO {
    public void printAttendanceReport(String registerNo, int courseId, String fromDate, String toDate) throws SQLException {
        String sql = """SELECT SUM(CASE WHEN ar.status = 'P' THEN 1 ELSE 0 END) AS present_days,
                     COUNT(*) AS total_days,
                     ROUND(100.0 * SUM(CASE WHEN ar.status = 'P' THEN 1 ELSE 0 END) / COUNT(*),2) AS attendance_pct
                     FROM AttendanceRecord ar
                     WHERE ar.register_no = ? AND ar.course_id = ? AND ar.att_date BETWEEN ? AND ?""";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registerNo);
            ps.setInt(2, courseId);
            ps.setString(3, fromDate);
            ps.setString(4, toDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Attendance Report for " + registerNo + " in course " + courseId);
                    System.out.println("Present days: " + rs.getInt("present_days"));
                    System.out.println("Total days: " + rs.getInt("total_days"));
                    System.out.println("Attendance %: " + rs.getDouble("attendance_pct"));
                } else {
                    System.out.println("No attendance records found.");
                }
            }
        }
    }
}
