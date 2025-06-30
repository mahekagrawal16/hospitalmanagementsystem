package com.docvisitnow.util;

import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/docvisitnow?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mahek@16";

    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // If the driver is not found, throw a runtime exception
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        // Now establish the connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
