/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crud_demo.servlets;

import com.crud_demo.utils.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserDemo", urlPatterns = {"/UserDemo"})
public class UserDemo extends HttpServlet {

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
            rs = stmt.executeQuery("SELECT * FROM user_datanew");
            System.out.println(rs + "hello");
            // Dynamically generate HTML for the table rows
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td class=\"myData\"><input type=\"checkbox\" class=\"checkbox\"/></td>");
                out.println("<td class='myData'>" + rs.getString("PINSTID") + "</td>");
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get selected IDs from the request

        String selectedIds = request.getParameter("selectedIds");
        if (selectedIds == null || selectedIds.trim().isEmpty()) {
            response.getWriter().write("No rows selected.");
            return;
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        // Split comma-separated IDs into an array
        String[] ids = selectedIds.split(",");

        // Update the database
        try {
            conn = DB.getConnection();
            String updateQuery = "UPDATE user_datanew SET ISUPDATED = '1' WHERE PinstID = ?";
            stmt = conn.prepareStatement(updateQuery);

            for (String id : ids) {
                stmt.setString(1, id.trim()); // Bind each ID to the query
                stmt.executeUpdate(); // Execute the update
            }

            response.getWriter().write("Selected rows updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error updating rows.");
        }
    }

}
