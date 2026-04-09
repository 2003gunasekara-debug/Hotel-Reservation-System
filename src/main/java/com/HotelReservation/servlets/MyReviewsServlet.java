package com.HotelReservation.servlets;

import com.HotelReservation.dao.ReviewDAO;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyReviewsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+log+in+to+view+reviews");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            int touristId = Integer.parseInt(session.getAttribute("touristId").toString());
            ReviewDAO reviewDAO = new ReviewDAO(con);
            request.setAttribute("reviews", reviewDAO.getReviewsByTouristId(touristId));
            request.getRequestDispatcher("/myReviews.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/touristDashboard.jsp?error=Invalid+user+ID");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load reviews: " + e.getMessage());
            request.getRequestDispatcher("/myReviews.jsp").forward(request, response);
        }
    }
}