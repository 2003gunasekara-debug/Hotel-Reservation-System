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

public class EditBookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            LocalDate checkIn = LocalDate.parse(req.getParameter("checkIn"));
            LocalDate checkOut = LocalDate.parse(req.getParameter("checkOut"));
            int guests = Integer.parseInt(req.getParameter("guests"));
            String status = req.getParameter("status");

            if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                throw new ServletException("Check-out date must be after check-in date");
            }
            if (guests <= 0) {
                throw new ServletException("Number of guests must be positive");
            }

            try (Connection con = DBUtil.getConnection()) {
                BookingDAO dao = new BookingDAO(con);
                Booking b = dao.getBookingById(id);
                if (b != null) {
                    b.setCheckIn(checkIn);
                    b.setCheckOut(checkOut);
                    b.setGuests(guests);
                    b.setStatus(status);
                    dao.updateBooking(b);
                }
                resp.sendRedirect("ViewBookingsServlet");
            }
        } catch (Exception e) {
            System.err.println("Error editing booking: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error editing booking", e);
        }
    }
}