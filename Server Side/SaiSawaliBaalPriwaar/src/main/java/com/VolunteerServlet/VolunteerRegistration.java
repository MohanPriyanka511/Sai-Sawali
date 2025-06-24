package com.VolunteerServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/VolunteerRegistration")
public class VolunteerRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String area = request.getParameter("area");
        String availability = request.getParameter("availability");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Volunteer Registration</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f5f7fa; text-align: center; padding: 50px; }");
        out.println(".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); display: inline-block; }");
        out.println("h3 { color: green; }");
        out.println(".error { color: red; }");
        out.println("a { text-decoration: none; color: #fff; background: #007bff; padding: 10px 20px; border-radius: 5px; display: inline-block; margin-top: 20px; }");
        out.println("a:hover { background: #0056b3; }");
        out.println("</style></head><body>");
        out.println("<div class='container'>");

        
        try {
            // Update DB details as needed
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/saisawali", "root", ""
            );

            String query = "INSERT INTO volunteers (name, email, phone, area, availability) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, area);
            ps.setString(5, availability);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h3>✅ Registration Successful!</h3>");
                out.println("<a href='http://127.0.0.1:5501/index.html'>Return to Home Page</a>");
            } else {
                out.println("<h3 class='error'>❌ Error occurred during registration.</h3>");
            }

            con.close();
        } catch (Exception e) {
        	 out.println("<h3 class='error'>⚠️ Exception: " + e.getMessage() + "</h3>");        
        }
        out.println("</div></body></html>");
    }
    
}
	