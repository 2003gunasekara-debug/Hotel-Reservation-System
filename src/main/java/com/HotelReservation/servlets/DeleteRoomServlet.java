package com.HotelReservation.servlets;

import com.HotelReservation.dao.RoomDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteRoomServlet extends HttpServlet {
    private RoomDAO roomDAO = new RoomDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int hotelId = 0;

        try {
            // Get parameters from request
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            hotelId = Integer.parseInt(req.getParameter("hotelId"));

            // Delete the room
            boolean deleted = roomDAO.deleteRoom(roomId);

            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/ListRoomsServlet?hotelId=" + hotelId + "&success=Room+deleted+successfully");
            } else {
                resp.sendRedirect(req.getContextPath() + "/ListRoomsServlet?hotelId=" + hotelId + "&error=Failed+to+delete+room");
            }

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ListRoomsServlet?hotelId=" + hotelId + "&error=Invalid+room+ID");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/ListRoomsServlet?hotelId=" + hotelId + "&error=Error+deleting+room:+" + e.getMessage());
        }
    }

    // Alternative: Support GET requests for deletion (with confirmation)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }
}