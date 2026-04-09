package com.HotelReservation.servlets;

import com.HotelReservation.dao.BookingDAO;
import com.HotelReservation.model.Booking;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

public class AddBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            int touristId = (int) session.getAttribute("touristId");
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            LocalDate checkIn = LocalDate.parse(req.getParameter("checkIn"));
            LocalDate checkOut = LocalDate.parse(req.getParameter("checkOut"));
            int guests = Integer.parseInt(req.getParameter("guests"));

            // Validate dates
            if (checkIn.isAfter(checkOut) || checkIn.isBefore(LocalDate.now())) {
                session.setAttribute("error", "Invalid date selection");
                resp.sendRedirect("TouristDashboardServlet");
                return;
            }

            try (Connection con = DBUtil.getConnection()) {
                BookingDAO dao = new BookingDAO(con);
                Booking b = new Booking(0, roomId, touristId, checkIn, checkOut, "Pending", guests);

                dao.addBooking(b); // insert into DB

                session.setAttribute("success", "Booking added successfully!");
                resp.sendRedirect("TouristDashboardServlet"); // back to dashboard

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to add booking. Please try again.");
                resp.sendRedirect("TouristDashboardServlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session1 = req.getSession();
            session1.setAttribute("error", "Error adding booking: " + e.getMessage());
            resp.sendRedirect("TouristDashboardServlet");
        }
    }
}