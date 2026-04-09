package com.HotelReservation.servlets;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class BuyRoomServlet extends HttpServlet {
    private RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rid = request.getParameter("roomId");
        if (rid == null) {
            response.sendRedirect("index.jsp"); // fallback
            return;
        }

        try {
            int roomId = Integer.parseInt(rid);
            Room room = roomDAO.getRoomById(roomId);
            if (room == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            request.setAttribute("room", room);
            request.getRequestDispatcher("/payment.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}