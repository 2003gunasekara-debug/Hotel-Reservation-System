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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UpdateProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateProfileServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("owner") == null) {
            logger.warn("No owner found in session, redirecting to login");
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+login+to+update+profile");
            return;
        }

        Owner owner = (Owner) session.getAttribute("owner");
        int id = owner.getId();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");

        // Validation
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            logger.warn("Invalid input: name or email is empty for ownerId: {}", id);
            resp.sendRedirect(req.getContextPath() + "/ProfileServlet?error=Name+and+email+are+required");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            OwnerDAO dao = new OwnerDAO(con);
            Owner updatedOwner = new Owner(id, name, email, password != null && !password.isEmpty() ? password : owner.getPassword(), phone);
            boolean updated = dao.updateOwner(updatedOwner);
            if (updated) {
                logger.info("Owner profile updated successfully for ownerId: {}", id);
                session.setAttribute("owner", updatedOwner); // Update session with new owner data
                resp.sendRedirect(req.getContextPath() + "/ProfileServlet?success=Profile+updated+successfully");
            } else {
                logger.warn("No rows affected when updating owner profile for ownerId: {}", id);
                resp.sendRedirect(req.getContextPath() + "/ProfileServlet?error=Failed+to+update+profile");
            }
        } catch (SQLException e) {
            logger.error("Database error updating owner profile for ownerId: {}", id, e);
            resp.sendRedirect(req.getContextPath() + "/ProfileServlet?error=Database+error:+ " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error updating owner profile for ownerId: {}", id, e);
            throw new ServletException("Error updating profile", e);
        }
    }
}