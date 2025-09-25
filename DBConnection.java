package com.sis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // UPDATE these values for your local MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/sis_jdbc?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "your_password_here";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void closeAuto(AutoCloseable ac) {
        if (ac != null) {
            try { ac.close(); } catch (Exception ignored) {}
        }
    }
}
