package com.HotelReservation.servlets;

import com.HotelReservation.dao.BookingDAO;
import com.HotelReservation.dao.HotelDAO;
import com.HotelReservation.model.Booking;
import com.HotelReservation.model.Hotel;
import com.HotelReservation.util.DBUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TouristDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect("login.jsp?error=2");
            return;
        }

        int touristId = (int) session.getAttribute("touristId");
        System.out.println("Loading dashboard for tourist ID: " + touristId);

        try (Connection con = DBUtil.getConnection()) {
            System.out.println("Database connection established");

            HotelDAO hotelDAO = new HotelDAO(con);
            BookingDAO bookingDAO = new BookingDAO(con);

            // Get search query if present
            String searchQuery = req.getParameter("searchQuery");
            List<Hotel> hotels = new ArrayList<>();
            try {
                // If there's a search query, filter hotels by name; otherwise, get all hotels
                hotels = hotelDAO.getAllHotels();
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    final String query = searchQuery.trim().toLowerCase();
                    hotels = hotels.stream()
                            .filter(hotel -> hotel.getName().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    req.setAttribute("searchQuery", searchQuery); // Pass search query back to JSP
                }
                System.out.println("Hotels found: " + (hotels != null ? hotels.size() : "null"));
            } catch (Exception e) {
                System.err.println("Error getting hotels: " + e.getMessage());
                e.printStackTrace();
                // Continue with empty hotels list
            }

            // Get bookings
            List<Booking> bookings = new ArrayList<>();
            try {
                bookings = bookingDAO.getBookingsByTourist(touristId);
                System.out.println("Bookings found: " + (bookings != null ? bookings.size() : "null"));
            } catch (Exception e) {
                System.err.println("Error getting bookings: " + e.getMessage());
                e.printStackTrace();
                // Continue with empty bookings list
            }

            // Ensure lists are never null
            req.setAttribute("hotels", hotels != null ? hotels : new ArrayList<>());
            req.setAttribute("bookings", bookings != null ? bookings : new ArrayList<>());

            RequestDispatcher rd = req.getRequestDispatcher("touristDashboard.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            System.err.println("Critical error in TouristDashboardServlet: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "Unable to load dashboard. Please try again.");
            resp.sendRedirect("login.jsp?error=3");
        }
    }
}