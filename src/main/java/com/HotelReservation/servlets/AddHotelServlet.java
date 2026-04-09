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
public class AddHotelServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddHotelServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Owner owner = (Owner) session.getAttribute("owner");
        if (owner == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Integer ownerId = owner.getId();
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String amenities = request.getParameter("amenities");

        if (name == null || location == null || name.trim().isEmpty() || location.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/hotel_owner/addHotel.jsp?error=Name+and+Location+are+required");
            return;
        }

        Part imagePart = request.getPart("image");
        String fileName = (imagePart != null && imagePart.getSize() > 0) ?
                Paths.get(imagePart.getSubmittedFileName()).getFileName().toString() : "default.jpg";
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        if (imagePart != null && imagePart.getSize() > 0) {
            imagePart.write(uploadPath + File.separator + fileName);
        }
        String imagePath = "uploads/" + fileName;

        Hotel hotel = new Hotel(0, ownerId, name, location, description, imagePath, amenities);

        try {
            new HotelDAO().addHotel(hotel);
            logger.info("Hotel added successfully: {} for ownerId: {}", name, ownerId);
            response.sendRedirect(request.getContextPath() + "/index.jsp?success=Hotel+added+successfully");
        } catch (SQLException e) {
            logger.error("Failed to add hotel: {} for ownerId: {}", name, ownerId, e);
            response.sendRedirect(request.getContextPath() + "/hotel_owner/addHotel.jsp?error=Unable+to+add+hotel:+" + e.getMessage());
        }
    }
}