package com.HotelReservation.servlets;

import com.HotelReservation.dao.BookingDAO;
import com.HotelReservation.dao.HotelDAO;
import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Hotel;
import com.HotelReservation.model.Room;
import com.HotelReservation.util.DBUtil;

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

public class ViewHotelRoomsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect("login.jsp?error=2");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            int hotelId = Integer.parseInt(req.getParameter("hotelId"));
            HotelDAO hotelDAO = new HotelDAO(con);
            Hotel hotel = hotelDAO.getHotelById(hotelId);
            if (hotel == null) {
                resp.sendRedirect("TouristDashboardServlet?error=invalidHotelId");
                return;
            }
            RoomDAO roomDAO = new RoomDAO(con);
            BookingDAO bookingDAO = new BookingDAO(con);
            List<Room> allRooms = roomDAO.getRoomsByHotelId(hotelId);
            List<Room> availableRooms = new ArrayList<>();

            // Get search query for room type if present
            String roomTypeQuery = req.getParameter("roomTypeQuery");

            // Filter out rooms with active bookings
            for (Room room : allRooms) {
                if (!bookingDAO.hasActiveBookings(room.getId())) {
                    availableRooms.add(room);
                }
            }

            // If there's a room type search query, filter available rooms by room type
            if (roomTypeQuery != null && !roomTypeQuery.trim().isEmpty()) {
                final String query = roomTypeQuery.trim().toLowerCase();
                availableRooms = availableRooms.stream()
                        .filter(room -> room.getRoomType().toLowerCase().contains(query))
                        .collect(Collectors.toList());
                req.setAttribute("roomTypeQuery", roomTypeQuery); // Pass search query back to JSP
            }

            req.setAttribute("rooms", availableRooms);
            req.setAttribute("hotelName", hotel.getName());
            req.getRequestDispatcher("hotelRooms.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("TouristDashboardServlet?error=invalidHotelId");
        } catch (Exception e) {
            System.err.println("Error retrieving rooms for hotelId: " + req.getParameter("hotelId"));
            e.printStackTrace();
            throw new ServletException("Error retrieving rooms", e);
        }
    }
}