package com.HotelReservation.servlets;

import com.HotelReservation.dao.OwnerDAO;
import com.HotelReservation.model.Owner;
import com.HotelReservation.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class OwnerSignupServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OwnerSignupServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            logger.warn("Invalid input data for owner signup: {}", email);
            response.sendRedirect(request.getContextPath() + "/ownerSignup.jsp?error=All fields are required");
            return;
        }

        Owner newOwner = new Owner(0, name, email, password, phone); // ID will be auto-incremented by DB
        try (Connection con = DBUtil.getConnection()) {
            OwnerDAO dao = new OwnerDAO(con);
            if (dao.getOwnerByUsername(email) != null) {
                logger.warn("Email already registered: {}", email);
                response.sendRedirect(request.getContextPath() + "/ownerSignup.jsp?error=Email already registered");
                return;
            }
            if (dao.registerOwner(newOwner)) {
                logger.info("New owner registered successfully: {}", email);
                response.sendRedirect(request.getContextPath() + "/index.jsp?success=Owner registered successfully");
            } else {
                logger.error("Failed to register owner: {}", email);
                response.sendRedirect(request.getContextPath() + "/ownerSignup.jsp?error=Registration failed");
            }
        } catch (SQLException e) {
            logger.error("Database error during owner registration for email: {}", email, e);
            response.sendRedirect(request.getContextPath() + "/ownerSignup.jsp?error=Database error: " + e.getMessage());
        }
    }
}