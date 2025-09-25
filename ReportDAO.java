package com.sis.dao;

import com.sis.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportDAO {
    public void printGradeReport(String registerNo, int semesterId) throws SQLException {
        String sql = """SELECT c.course_code, c.title, e.marks, e.grade, e.grade_points, c.credits
                     FROM Enrollment e JOIN Course c ON e.course_id = c.course_id
                     WHERE e.register_no = ? AND e.semester_id = ?""";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registerNo);
            ps.setInt(2, semesterId);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Grade report for " + registerNo);
                while (rs.next()) {
                    System.out.printf("%s | %s | marks=%d | grade=%s | gp=%.2f | credits=%d%n",
                            rs.getString("course_code"), rs.getString("title"),
                            rs.getInt("marks"), rs.getString("grade"), rs.getDouble("grade_points"), rs.getInt("credits"));
                }
            }
        }
    }

    public void printGPA(String registerNo, int semesterId) throws SQLException {
        String sql = """SELECT SUM(e.grade_points * c.credits) / SUM(c.credits) AS gpa
                     FROM Enrollment e JOIN Course c ON e.course_id = c.course_id
                     WHERE e.register_no = ? AND e.semester_id = ?""";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registerNo);
            ps.setInt(2, semesterId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) System.out.println("GPA: " + rs.getDouble("gpa"));
                else System.out.println("No records.");
            }
        }
    }
}
