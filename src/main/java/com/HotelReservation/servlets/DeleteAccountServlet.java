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

public class DeleteAccountServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteAccountServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("owner") == null) {
            logger.warn("No owner found in session, redirecting to login");
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+login+to+delete+account");
            return;
        }

        int id = ((Owner) session.getAttribute("owner")).getId();
        try (Connection con = DBUtil.getConnection()) {
            OwnerDAO dao = new OwnerDAO(con);
            boolean deleted = dao.deleteOwner(id);
            if (deleted) {
                logger.info("Owner account deleted successfully for ownerId: {}", id);
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login.jsp?success=Account+deleted+successfully");
            } else {
                logger.warn("No rows affected when deleting owner account for ownerId: {}", id);
                resp.sendRedirect(req.getContextPath() + "/ownerProfile.jsp?error=Failed+to+delete+account");
            }
        } catch (SQLException e) {
            logger.error("Database error deleting owner account for ownerId: {}", id, e);
            resp.sendRedirect(req.getContextPath() + "/ownerProfile.jsp?error=Database+error:+ " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error deleting owner account for ownerId: {}", id, e);
            throw new ServletException("Error deleting account", e);
        }
    }
}