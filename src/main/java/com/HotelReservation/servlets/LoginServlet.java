package com.HotelReservation.servlets;

import com.HotelReservation.dao.AdminDAO;
import com.HotelReservation.dao.OwnerDAO;
import com.HotelReservation.dao.TouristDAO;
import com.HotelReservation.model.Admin;
import com.HotelReservation.model.Owner;
import com.HotelReservation.model.Tourist;
import com.HotelReservation.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        logger.info("Login attempt for email: {}", email);

        // Basic null check
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            logger.warn("Empty email or password provided");
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            // Check if user is an admin
            AdminDAO adminDAO = new AdminDAO(con);
            Admin admin = adminDAO.getAdminByEmail(email);

            if (admin != null && password.equals(admin.getPassword())) {
                // Admin login successful
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                logger.info("Login successful for admin: {} (ID: {})", admin.getFullName(), admin.getId());
                response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
                return;
            }

            // Check if user is an owner
            OwnerDAO ownerDAO = new OwnerDAO(con);
            Owner owner = ownerDAO.getOwnerByUsername(email);

            if (owner != null && password.equals(owner.getPassword())) {
                // Owner login successful
                HttpSession session = request.getSession();
                session.setAttribute("owner", owner);
                logger.info("Login successful for owner: {} (ID: {})", owner.getName(), owner.getId());
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            // Check if user is a tourist
            TouristDAO touristDAO = new TouristDAO(con);
            Tourist tourist = touristDAO.login(email, password);

            if (tourist != null) {
                // Tourist login successful
                HttpSession session = request.getSession();
                session.setAttribute("touristId", tourist.getId());
                session.setAttribute("touristName", tourist.getName());
                logger.info("Login successful for tourist: {} (ID: {})", tourist.getName(), tourist.getId());
                response.sendRedirect(request.getContextPath() + "/TouristDashboardServlet");
                return;
            }

            // Invalid credentials
            logger.warn("Invalid login attempt for email: {}", email);
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Login failed for email: {}", email, e);
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}