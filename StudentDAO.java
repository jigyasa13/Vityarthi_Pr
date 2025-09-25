package com.sis.dao;

import com.sis.model.Student;
import com.sis.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO Student(register_no, first_name, last_name) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getRegisterNo());
            ps.setString(2, s.getFirstName());
            ps.setString(3, s.getLastName());
            ps.executeUpdate();
        }
    }

    public Student getStudent(String regNo) throws SQLException {
        String sql = "SELECT register_no, first_name, last_name FROM Student WHERE register_no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Student(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        }
        return null;
    }

    public List<Student> listAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT register_no, first_name, last_name FROM Student";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return list;
    }
}
