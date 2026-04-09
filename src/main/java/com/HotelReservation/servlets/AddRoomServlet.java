package com.HotelReservation.servlets;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Room;
import com.HotelReservation.strategy.RoomValidationStrategy;
import com.HotelReservation.strategy.StandardRoomValidationStrategy;

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
public class AddRoomServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AddRoomServlet.class);
    private final RoomDAO roomDAO = new RoomDAO();
    private RoomValidationStrategy validationStrategy;

    @Override
    public void init() throws ServletException {
        // Initialize with default strategy
        this.validationStrategy = new StandardRoomValidationStrategy();
    }

    public void setValidationStrategy(RoomValidationStrategy strategy) {
        this.validationStrategy = strategy;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int hotelId = 0;
        String errorMessage = null;

        try {
            hotelId = Integer.parseInt(req.getParameter("hotelId"));
            String roomNumber = req.getParameter("roomNumber");
            String roomType = req.getParameter("roomType");
            double price = Double.parseDouble(req.getParameter("price"));
            String amenities = req.getParameter("amenities") != null ? req.getParameter("amenities") : "";

            // Validate using strategy
            errorMessage = validationStrategy.validate(hotelId, roomNumber, roomType, price, roomDAO);

            if (errorMessage != null) {
                redirectWithError(resp, req, hotelId, errorMessage);
                return;
            }

            // Handle image upload
            Part imagePart = req.getPart("image");
            String fileName = (imagePart != null && imagePart.getSize() > 0) ?
                    Paths.get(imagePart.getSubmittedFileName()).getFileName().toString() : "default_room.jpg";
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            if (imagePart != null && imagePart.getSize() > 0) {
                imagePart.write(uploadPath + File.separator + fileName);
            }
            String imagePath = "uploads/" + fileName;

            Room room = new Room(hotelId, roomNumber, roomType, price, imagePath, amenities);
            roomDAO.addRoom(room);

            logger.info("Room added successfully: {} for hotelId: {}", roomNumber, hotelId);
            resp.sendRedirect(req.getContextPath() +
                    "/ListRoomsServlet?hotelId=" + hotelId + "&success=Room+added+successfully");

        } catch (NumberFormatException e) {
            logger.error("Invalid number format for hotelId or price", e);
            redirectWithError(resp, req, hotelId, "Invalid number format");
        } catch (Exception e) {
            logger.error("Error adding room for hotelId: {}", hotelId, e);
            redirectWithError(resp, req, hotelId, "Error adding room: " + e.getMessage());
        }
    }

    private void redirectWithError(HttpServletResponse resp, HttpServletRequest req,
                                   int hotelId, String msg) throws IOException {
        resp.sendRedirect(req.getContextPath() +
                "/hotel_owner/rooms.jsp?hotelId=" + hotelId + "&error=" + msg);
    }
}