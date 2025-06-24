package com.contactmsg;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class contactmessage
 */
@WebServlet("/contactmessage")
public class contactmessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public contactmessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
		response.setContentType("application/json");
		System.out.println("Connected...");
		
		PrintWriter ps = response.getWriter();
//		ps.print("Done");
		
		try {
			
//			Loading jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connection Establish..!!");
			
//			database connection
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SaiSawali","root","");
			
			PreparedStatement ps1 = con.prepareStatement("insert into contactmsg (name , email, subject, message) values(?,?,?,?)");
//			int r = Integer.parseInt(request.getParameter("studroll"));
			String un = request.getParameter("username");
			String ei = request.getParameter("emailid");
			String s = request.getParameter("subject");
			String a = request.getParameter("msg");

			ps1.setString(1, un);
			ps1.setString(2, ei);
			ps1.setString(3, s);
			ps1.setString(4, a);
			int count = ps1.executeUpdate();
//			System.out.println("Inserted Successfully"+count);
			if(count>0) {
//				String url = request.getHeader("Refere");
				response.sendRedirect("http://127.0.0.1:5501/contactUs.html");
				System.out.print("Inserted Successfully"+un);
			}
			else {
				System.out.print("Failed..");
			
			}
		}
			catch(Exception e) {
				System.out.println(e);
			}


	}

}
