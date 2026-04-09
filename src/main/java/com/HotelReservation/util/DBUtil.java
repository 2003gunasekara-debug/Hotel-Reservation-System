package com.HotelReservation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Hotel_Website;encrypt=true;trustServerCertificate=true";
    private static final String USER = "Lithika";
    private static final String PASSWORD = "lithika2004";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    // ---------- Test main method ----------
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connection successful!");
            } else {
                System.out.println("❌ Connection failed (connection is null or closed).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}