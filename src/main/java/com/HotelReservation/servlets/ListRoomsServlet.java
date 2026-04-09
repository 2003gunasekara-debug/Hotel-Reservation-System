package com.HotelReservation.servlets;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListRoomsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ListRoomsServlet.class);
    private RoomDAO roomDAO = new RoomDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Processing ListRoomsServlet for hotelId: {}", req.getParameter("hotelId"));
        try {
            String hotelIdParam = req.getParameter("hotelId");
            if (hotelIdParam == null || hotelIdParam.trim().isEmpty()) {
                logger.error("hotelId parameter is missing or empty");
                resp.sendRedirect(req.getContextPath() + "/hotel_owner/rooms.jsp?error=Missing+hotel+ID");
                return;
            }

            int hotelId = Integer.parseInt(hotelIdParam);
            logger.debug("Fetching rooms for hotelId: {}", hotelId);
            List<Room> rooms = roomDAO.getRoomsByHotel(hotelId);
            req.setAttribute("rooms", rooms);
            req.setAttribute("hotelId", hotelId);
            logger.debug("Forwarding to rooms.jsp with {} rooms", rooms.size());
            req.getRequestDispatcher("/hotel_owner/rooms.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            logger.error("Invalid hotel ID: {}", req.getParameter("hotelId"), e);
            resp.sendRedirect(req.getContextPath() + "/hotel_owner/rooms.jsp?error=Invalid+hotel+ID");
        } catch (Exception e) {
            logger.error("Error in ListRoomsServlet", e);
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/hotel_owner/rooms.jsp?error=Error+loading+rooms:+" + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}