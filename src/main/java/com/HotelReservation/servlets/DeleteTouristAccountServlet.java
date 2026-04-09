package com.HotelReservation.servlets;

import com.HotelReservation.dao.TouristDAO;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class DeleteTouristAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer touristId = (Integer) session.getAttribute("touristId");
        if (touristId == null) {
            resp.sendRedirect("login.jsp?error=Please login to delete your account");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            TouristDAO dao = new TouristDAO(con);
            if (dao.delete(touristId)) {
                session.invalidate();
                resp.sendRedirect("login.jsp?success=Account deleted successfully");
            } else {
                resp.sendRedirect("touristProfile.jsp?error=Failed to delete account");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}