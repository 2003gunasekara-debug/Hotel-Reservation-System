package com.HotelReservation.servlets;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MultipartConfig
public class EditRoomServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EditRoomServlet.class);
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            int hotelId = Integer.parseInt(req.getParameter("hotelId"));

            Room room = roomDAO.getRoomById(roomId);
            if (room != null) {
                req.setAttribute("room", room);
                req.getRequestDispatcher("/hotel_owner/editRoom.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() +
                        "/ListRoomsServlet?hotelId=" + hotelId + "&error=Room+not+found");
            }
        } catch (Exception e) {
            logger.error("Unable to load room details", e);
            throw new ServletException("Unable to load room details", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int hotelId = 0;
        int roomId = 0;
        String errorMessage = null;

        try {
            roomId = Integer.parseInt(req.getParameter("roomId"));
            hotelId = Integer.parseInt(req.getParameter("hotelId"));
            String roomNumber = req.getParameter("roomNumber");
            String roomType = req.getParameter("roomType");
            double price = Double.parseDouble(req.getParameter("price"));
            String amenities = req.getParameter("amenities") != null ? req.getParameter("amenities") : "";

            // Validation
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                errorMessage = "Room number is required";
            } else if (roomType == null || roomType.trim().isEmpty()) {
                errorMessage = "Room type is required";
            } else if (price <= 0) {
                errorMessage = "Price must be greater than 0";
            }

            if (errorMessage != null) {
                redirectWithError(resp, req, hotelId, roomId, errorMessage);
                return;
            }

            // Uniqueness check (ignore same room)
            if (roomDAO.isRoomNumberExists(hotelId, roomNumber, roomId)) {
                redirectWithError(resp, req, hotelId, roomId, "Room number already exists");
                return;
            }

            // Handle image upload
            Part imagePart = req.getPart("image");
            String imagePath;
            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                imagePart.write(uploadPath + File.separator + fileName);
                imagePath = "uploads/" + fileName;
            } else {
                // Retain existing image if no new image is uploaded
                Room existingRoom = roomDAO.getRoomById(roomId);
                imagePath = (existingRoom != null) ? existingRoom.getImagePath() : "uploads/default_room.jpg";
            }

            // Update
            Room existingRoom = roomDAO.getRoomById(roomId);
            if (existingRoom != null) {
                existingRoom.setRoomNumber(roomNumber);
                existingRoom.setRoomType(roomType);
                existingRoom.setPrice(price);
                existingRoom.setImagePath(imagePath);
                existingRoom.setAmenities(amenities);

                roomDAO.updateRoom(existingRoom);
                logger.info("Room updated successfully: {} for hotelId: {}", roomNumber, hotelId);
                resp.sendRedirect(req.getContextPath() +
                        "/ListRoomsServlet?hotelId=" + hotelId + "&success=Room+updated+successfully");
            } else {
                redirectWithError(resp, req, hotelId, roomId, "Room not found");
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid number format for roomId or price", e);
            redirectWithError(resp, req, hotelId, roomId, "Invalid number format");
        } catch (Exception e) {
            logger.error("Error updating room for roomId: {}", roomId, e);
            redirectWithError(resp, req, hotelId, roomId, "Error updating room: " + e.getMessage());
        }
    }

    private void redirectWithError(HttpServletResponse resp, HttpServletRequest req,
                                   int hotelId, int roomId, String msg) throws IOException {
        resp.sendRedirect(req.getContextPath() +
                "/EditRoomServlet?roomId=" + roomId + "&hotelId=" + hotelId + "&error=" + msg);
    }
}