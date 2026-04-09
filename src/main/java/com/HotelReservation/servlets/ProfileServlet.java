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

public class ProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("owner") == null) {
            logger.warn("No owner found in session, redirecting to login");
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+login+to+view+profile");
            return;
        }

        Owner owner = (Owner) session.getAttribute("owner");
        int ownerId = owner.getId();
        try (Connection con = DBUtil.getConnection()) {
            OwnerDAO dao = new OwnerDAO(con);
            Owner freshOwner = dao.getOwnerById(ownerId);
            if (freshOwner != null) {
                req.setAttribute("owner", freshOwner);
                session.setAttribute("owner", freshOwner); // Refresh session data
                logger.info("Owner profile retrieved for ownerId: {}", ownerId);
                req.getRequestDispatcher("/ownerProfile.jsp").forward(req, resp);
            } else {
                logger.warn("Owner not found in database for ownerId: {}", ownerId);
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Owner+not+found");
            }
        } catch (SQLException e) {
            logger.error("Database error retrieving owner profile for ownerId: {}", ownerId, e);
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Database+error:+ " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving owner profile for ownerId: {}", ownerId, e);
            throw new ServletException("Error retrieving profile", e);
        }
    }
}