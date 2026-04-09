package com.HotelReservation.servlets;

import com.HotelReservation.dao.HotelDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteHotelServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int hotelId;
        try {
            hotelId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=Invalid+hotel+ID");
            return;
        }

        try {
            new HotelDAO().deleteHotel(hotelId);
            response.sendRedirect(request.getContextPath() + "/index.jsp?success=Hotel+deleted+successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Delete failed";

            // Check for foreign key constraint violation
            if (e.getMessage().contains("foreign key constraint") ||
                    e.getMessage().contains("REFERENCE") ||
                    e.getErrorCode() == 547) { // SQL Server foreign key violation code
                errorMessage = "Cannot delete hotel: There are related rooms or bookings. Please delete them first.";
            }

            response.sendRedirect(request.getContextPath() + "/index.jsp?error=" +
                    java.net.URLEncoder.encode(errorMessage, "UTF-8"));
        }
    }
}