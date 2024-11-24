package com.crud_demo.servlets;

import com.crud_demo.utils.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
//@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();
            System.out.println(conn + "hiiiiiiiiiiiiiiiiiiiiiiiii");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM user_information");
            System.out.println(rs + "hello");
            // Dynamically generate HTML for the table rows
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("User_Name") + "</td>");
                out.println("<td>" + rs.getString("User_Email") + "</td>");
                out.println("<td>" + rs.getString("User_Phone") + "</td>");
                out.println("<td><button onclick='editUser(" + rs.getInt("id") + ", \"" + rs.getString("User_Name") + "\", \"" + rs.getString("User_Email") + "\", \"" + rs.getString("User_Phone") + "\")' class='btn btn-warning'>Edit</button> <button onclick='deleteUser(" + rs.getInt("id") + ")' class='btn btn-danger'>Delete</button></td>");
                out.println("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error fetching users.</p>");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DB.getConnection();

            if ("save".equals(action)) {
                // Extract form data
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                // Insert query
                String query = "INSERT INTO user_information (User_Name, User_Email, User_Phone) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.executeUpdate();

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                // Delete query
                String query = "DELETE FROM user_information WHERE id = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Send success response
            response.getWriter().write("Operation successful");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error performing operation: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
