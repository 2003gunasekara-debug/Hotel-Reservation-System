package com.HotelReservation.servlets;

import com.HotelReservation.dao.AvailabilityDAO;
import com.HotelReservation.model.Availability;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateAvailabilityServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String dateStr = request.getParameter("date");
        boolean available = Boolean.parseBoolean(request.getParameter("available"));
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));

        Availability availability = new Availability(0, roomId, LocalDate.parse(dateStr), available);

        try {
            AvailabilityDAO dao = new AvailabilityDAO();
            // For simplicity, assume update; in real app, query by room_id and date to get id
            dao.updateAvailability(availability); // Adjust ID if needed via query
            response.sendRedirect("/hotel/hotel_owner/availability.jsp?hotelId=" + hotelId + "&success=Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/hotel/hotel_owner/availability.jsp?hotelId=" + hotelId + "&error=Update failed");
        }
    }
}