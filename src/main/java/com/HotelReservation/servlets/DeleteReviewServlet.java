package com.HotelReservation.servlets;

import com.HotelReservation.dao.ReviewDAO;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeleteReviewServlet")
public class DeleteReviewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+log+in+to+delete+reviews");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            String reviewIdStr = request.getParameter("reviewId");
            if (reviewIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/MyReviewsServlet?error=Invalid+review+ID");
                return;
            }

            int reviewId = Integer.parseInt(reviewIdStr);
            ReviewDAO reviewDAO = new ReviewDAO(con);
            reviewDAO.deleteReview(reviewId);

            request.setAttribute("message", "Review deleted successfully");
            request.getRequestDispatcher("/MyReviewsServlet").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/MyReviewsServlet?error=Invalid+review+ID");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to delete review: " + e.getMessage());
            request.getRequestDispatcher("/MyReviewsServlet").forward(request, response);
        }
    }
}