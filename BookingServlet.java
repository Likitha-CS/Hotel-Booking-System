package com.example.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hoteldb";
    private static final String DB_USER = "root"; // change as per your MySQL
    private static final String DB_PASS = "cslikitha"; // change as per your MySQL

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String roomType = request.getParameter("room_type");
        String checkIn = request.getParameter("check_in");
        String checkOut = request.getParameter("check_out");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            String sql = "INSERT INTO bookings (name, email, phone, room_type, check_in, check_out) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, roomType);
            ps.setString(5, checkIn);
            ps.setString(6, checkOut);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<h2>Booking Successful!</h2>");
                out.println("<p>Thank you, " + name + ". Your " + roomType + " room is booked from " + checkIn + " to " + checkOut + ".</p>");
            } else {
                out.println("<h2>Booking Failed</h2>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
        System.out.println(name+" " +email+" " +phone+" " +roomType+" " +checkIn+" " +checkOut);
    }
}
