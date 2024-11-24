package com.crud_demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "admin123");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }
    
//    public static void main(String[] args) {
//    try {
//        Connection conn = DB.getConnection();
//        if (conn != null) {
//            System.out.println("Database connected successfully!");
//        } else {
//            System.out.println("Failed to connect to the database.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}

}
