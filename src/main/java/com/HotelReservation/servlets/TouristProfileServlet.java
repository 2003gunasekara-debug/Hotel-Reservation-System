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

public class TouristProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer touristId = (Integer) session.getAttribute("touristId");
        if (touristId == null) {
            resp.sendRedirect("login.jsp?error=Please login to view your profile");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            TouristDAO dao = new TouristDAO(con);
            Tourist tourist = dao.getById(touristId);
            if (tourist == null) {
                resp.sendRedirect("touristProfile.jsp?error=Tourist not found");
                return;
            }
            req.setAttribute("tourist", tourist);
            req.getRequestDispatcher("/touristProfile.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}