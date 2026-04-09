package com.HotelReservation.servlets;

import com.HotelReservation.dao.BookingDAO;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class DeleteBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            try (Connection con = DBUtil.getConnection()) {
                BookingDAO dao = new BookingDAO(con);
                dao.deleteBooking(id);
                resp.sendRedirect("ViewBookingsServlet");
            }
        } catch (Exception e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error deleting booking", e);
        }
    }
}