package com.HotelReservation.servlets;

import com.HotelReservation.dao.HotelDAO;
import com.HotelReservation.dao.ReviewDAO;
import com.HotelReservation.model.Hotel;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewReviewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DBUtil.getConnection()) {
            int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            ReviewDAO reviewDAO = new ReviewDAO(con);
            HotelDAO hotelDAO = new HotelDAO(con);

            Hotel hotel = hotelDAO.getHotelById(hotelId);
            String hotelName = (hotel != null && hotel.getName() != null) ? hotel.getName() : "Unknown Hotel";

            request.setAttribute("reviews", reviewDAO.getReviewsByHotelId(hotelId));
            request.setAttribute("hotelName", hotelName);
            request.getRequestDispatcher("/viewReviews.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid hotel ID");
            request.getRequestDispatcher("/viewReviews.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load reviews: " + e.getMessage());
            request.getRequestDispatcher("/viewReviews.jsp").forward(request, response);
        }
    }
}