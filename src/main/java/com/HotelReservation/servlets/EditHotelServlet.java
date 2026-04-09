package com.HotelReservation.servlets;

import com.HotelReservation.dao.HotelDAO;
import com.HotelReservation.model.Hotel;
import com.HotelReservation.model.Owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MultipartConfig
public class EditHotelServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(EditHotelServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Owner owner = (Owner) session.getAttribute("owner");
        if (owner == null) {
            logger.warn("Unauthorized access attempt to edit hotel");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please+login+to+edit+hotel");
            return;
        }

        int hotelId;
        try {
            hotelId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Invalid hotel ID provided: {}", request.getParameter("id"), e);
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=Invalid+hotel+ID");
            return;
        }

        // Verify the hotel exists and belongs to the owner
        HotelDAO hotelDAO = new HotelDAO();
        Hotel hotel;
        try {
            hotel = hotelDAO.getHotelById(hotelId);
            if (hotel == null) {
                logger.error("Hotel not found for ID: {}", hotelId);
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=Hotel+not+found");
                return;
            }
            if (hotel.getOwnerId() != owner.getId()) {
                logger.warn("Owner {} attempted to edit hotel {} not owned by them", owner.getId(), hotelId);
                response.sendRedirect(request.getContextPath() + "/index.jsp?error=Unauthorized+to+edit+this+hotel");
                return;
            }
        } catch (SQLException e) {
            logger.error("Error retrieving hotel ID: {}", hotelId, e);
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=Database+error:+Unable+to+retrieve+hotel");
            return;
        }

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String amenities = request.getParameter("amenities");

        if (name == null || location == null || name.trim().isEmpty() || location.trim().isEmpty()) {
            logger.warn("Invalid input: name or location is empty for hotel ID: {}", hotelId);
            response.sendRedirect(request.getContextPath() + "/hotel_owner/editHotel.jsp?id=" + hotelId + "&error=Name+and+Location+are+required");
            return;
        }

        Part imagePart = request.getPart("image");
        String imagePath = request.getParameter("existingImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "Uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            imagePart.write(uploadPath + File.separator + fileName);
            imagePath = "Uploads/" + fileName;
        } else if (imagePath == null || imagePath.trim().isEmpty()) {
            imagePath = "Uploads/default.jpg";
        }

        Hotel updatedHotel = new Hotel(hotelId, owner.getId(), name, location, description, imagePath, amenities);

        try {
            hotelDAO.updateHotel(updatedHotel);
            logger.info("Hotel ID: {} updated successfully by owner ID: {}", hotelId, owner.getId());
            response.sendRedirect(request.getContextPath() + "/index.jsp?success=Hotel+updated+successfully");
        } catch (SQLException e) {
            logger.error("Failed to update hotel ID: {}", hotelId, e);
            response.sendRedirect(request.getContextPath() + "/hotel_owner/editHotel.jsp?id=" + hotelId + "&error=Update+failed:+" + e.getMessage());
        }
    }
}