package com.HotelReservation.servlets;

import com.HotelReservation.dao.BookingDAO;
import com.HotelReservation.dao.ReviewDAO;
import com.HotelReservation.model.Booking;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewBookingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+log+in+to+view+bookings");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            int touristId = Integer.parseInt(session.getAttribute("touristId").toString());
            BookingDAO bookingDAO = new BookingDAO(con);
            ReviewDAO reviewDAO = new ReviewDAO(con);
            List<Booking> bookings = bookingDAO.getBookingsByTouristId(touristId);

            // Fetch hotel IDs for bookings and check if reviews exist
            Map<Integer, Integer> bookingHotelMap = new HashMap<>();
            Map<Integer, Boolean> reviewExistsMap = new HashMap<>();
            for (Booking booking : bookings) {
                // Fetch hotel ID from rooms table
                String sql = "SELECT hotel_id FROM rooms WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, booking.getRoomId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            bookingHotelMap.put(booking.getId(), rs.getInt("hotel_id"));
                        }
                    }
                }
                // Check if review exists for this booking
                String reviewSql = "SELECT COUNT(*) FROM reviews WHERE booking_id = ?";
                try (PreparedStatement ps = con.prepareStatement(reviewSql)) {
                    ps.setInt(1, booking.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            reviewExistsMap.put(booking.getId(), rs.getInt(1) > 0);
                        }
                    }
                }
            }

            request.setAttribute("bookings", bookings != null ? bookings : new ArrayList<Booking>());
            request.setAttribute("bookingHotelMap", bookingHotelMap);
            request.setAttribute("reviewExistsMap", reviewExistsMap);
            request.getRequestDispatcher("/touristBookings.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/touristDashboard.jsp?error=Invalid+user+ID");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load bookings: " + e.getMessage());
            request.getRequestDispatcher("/touristBookings.jsp").forward(request, response);
        }
    }
}