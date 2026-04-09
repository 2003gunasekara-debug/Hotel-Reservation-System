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
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = DBUtil.getConnection()) {
            // Fetch all admins
            AdminDAO adminDAO = new AdminDAO(con);
            List<Admin> admins = adminDAO.getAllAdmins();

            // Fetch all owners
            OwnerDAO ownerDAO = new OwnerDAO(con);
            // Assuming a method to get all owners (modify OwnerDAO if needed)
            List<Owner> owners = ownerDAO.getAllOwners(); // You need to implement this method

            // Fetch all tourists
            TouristDAO touristDAO = new TouristDAO(con);
            // Assuming a method to get all tourists (modify TouristDAO if needed)
            List<Tourist> tourists = touristDAO.getAllTourists(); // You need to implement this method

            request.setAttribute("admins", admins);
            request.setAttribute("owners", owners);
            request.setAttribute("tourists", tourists);
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error loading admin dashboard", e);
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}