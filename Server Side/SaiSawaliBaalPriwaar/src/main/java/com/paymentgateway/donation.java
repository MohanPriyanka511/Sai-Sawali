package com.paymentgateway;

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

import org.json.JSONObject;
import com.razorpay.*;
/**
 * Servlet implementation class donation
 */
@WebServlet("/donation")
public class donation extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private RazorpayClient client;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public donation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @param status 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saisawali","root","");
			
			
			int amt = Integer.parseInt(request.getParameter("amount"));
//			int name = Integer.parseInt(request.getParameter("donername"));
			

//			System.out.println("Amount: "+amt+"Doner name:"+name);
//			ps.print(amt);
			
//			String apiKey = System.getenv("rzp_test_hKNc8WZcyPZ6wA");
//			String apiSecret = System.getenv("hzbF1Fkg1Bk2GqMKu32EYiVa");
//			RazorpayClient client = new RazorpayClient(apiKey, apiSecret);
			RazorpayClient client = new RazorpayClient("rzp_test_hKNc8WZcyPZ6wA","hzbF1Fkg1Bk2GqMKu32EYiVa");
			System.out.println(client);

			//		creating json object	
			
			JSONObject ob = new JSONObject();
			ob.put("amount", amt*100);
			ob.put("currency", "INR");
			ob.put("receipt", "txn_12345");
			System.out.println(ob);


			//		Creating order
			Order order = client.orders.create(ob);
			System.out.println("Order is created...."+order);
			ps.print(order.toString());

//			store the data in d
			PreparedStatement p = con.prepareStatement("insert into donation (amount, orderid, currency, status) values(?,?,?,?)");
			int amount = order.get("amount_due");
			String oid = order.get("id");
			String cuurency = order.get("currency");
			String status = order.get("status");
			p.setInt(1, amount);
			p.setString(2, oid);
			p.setString(3, cuurency);
			p.setString(4, status);
			int count = p.executeUpdate();
			
			if(count>0) {
			System.out.print("Inserted Successfully"+amount+" "+oid);
			}
			else
				System.out.print("Failed..");
			
			

		}catch(Exception e) {
			ps.print("Error"+e);
		}
		
	}

}
