package com.HotelReservation.servlets;

import com.HotelReservation.dao.TouristDAO;
import com.HotelReservation.model.Tourist;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class UpdateTouristProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer touristId = (Integer) session.getAttribute("touristId");
        if (touristId == null) {
            resp.sendRedirect("login.jsp?error=Please login to update your profile");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection con = DBUtil.getConnection()) {
            TouristDAO dao = new TouristDAO(con);
            Tourist tourist = dao.getById(touristId);
            if (tourist == null) {
                resp.sendRedirect("touristProfile.jsp?error=Tourist not found");
                return;
            }

            tourist.setName(name);
            tourist.setEmail(email);
            if (password != null && !password.trim().isEmpty()) {
                tourist.setPassword(password);
            }

            if (dao.update(tourist)) {
                session.setAttribute("touristName", name);
                resp.sendRedirect("touristProfile.jsp?success=Profile updated successfully");
            } else {
                resp.sendRedirect("touristProfile.jsp?error=Failed to update profile");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}