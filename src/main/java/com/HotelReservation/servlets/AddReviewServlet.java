package com.HotelReservation.servlets;

import com.HotelReservation.dao.ReviewDAO;
import com.HotelReservation.model.Review;
import com.HotelReservation.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class AddReviewServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "Uploads/reviews";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("touristId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+log+in+to+add+a+review");
            return;
        }

        String bookingIdStr = req.getParameter("bookingId");
        String hotelIdStr = req.getParameter("hotelId");
        String ratingStr = req.getParameter("rating");
        String description = req.getParameter("description");

        System.out.println("Received parameters - bookingId: " + bookingIdStr + ", hotelId: " + hotelIdStr + ", rating: " + ratingStr);

        if (isNullOrEmpty(bookingIdStr) || isNullOrEmpty(hotelIdStr) || isNullOrEmpty(ratingStr)) {
            session.setAttribute("error", "Error! Missing required parameters.");
            resp.sendRedirect("addReview.jsp?bookingId=" + (bookingIdStr != null ? bookingIdStr : "") + "&hotelId=" + (hotelIdStr != null ? hotelIdStr : ""));
            return;
        }

        try {
            int touristId = Integer.parseInt(session.getAttribute("touristId").toString());
            int bookingId = Integer.parseInt(bookingIdStr);
            int hotelId = Integer.parseInt(hotelIdStr);
            int rating = Integer.parseInt(ratingStr);

            String imagePath = handleFileUpload(req);
            Review review = new Review();
            review.setTouristId(touristId);
            review.setHotelId(hotelId);
            review.setBookingId(bookingId);
            review.setRating(rating);
            review.setDescription(description);
            review.setImagePath(imagePath != null ? imagePath : review.getImagePath());
            review.setCreatedAt(LocalDateTime.now());

            try (Connection con = DBUtil.getConnection()) {
                ReviewDAO reviewDAO = new ReviewDAO(con);
                reviewDAO.addReview(review);
                session.setAttribute("success", "Review added successfully!");
                resp.sendRedirect(req.getContextPath() + "/MyReviewsServlet");
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("error", "Database error: " + e.getMessage());
                resp.sendRedirect("addReview.jsp?bookingId=" + bookingIdStr + "&hotelId=" + hotelIdStr);
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid number formats");
            String bookingId = req.getParameter("bookingId");
            String hotelId = req.getParameter("hotelId");
            resp.sendRedirect("addReview.jsp?bookingId=" + (bookingId != null ? bookingId : "") + "&hotelId=" + (hotelId != null ? hotelId : ""));
        } catch (IOException e) {
            e.printStackTrace();
            session.setAttribute("error", "File upload failed: " + e.getMessage());
            resp.sendRedirect("addReview.jsp?bookingId=" + req.getParameter("bookingId") + "&hotelId=" + req.getParameter("hotelId"));
        }
    }

    private String handleFileUpload(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("image");
        if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().trim().isEmpty()) {
            String originalFileName = new File(filePart.getSubmittedFileName()).getName();
            String fileName = System.currentTimeMillis() + "_" + originalFileName; // Prevent filename conflicts
            String relativeUploadDir = "Uploads/reviews";
            String applicationPath = req.getServletContext().getRealPath("");

            File uploadDir = new File(applicationPath, relativeUploadDir);
            System.out.println("Upload directory: " + uploadDir.getAbsolutePath());

            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Failed to create directory: " + uploadDir.getAbsolutePath());
                }
            }

            String filePath = uploadDir.getAbsolutePath() + File.separator + fileName;
            System.out.println("Saving file to: " + filePath);
            filePart.write(filePath);

            return relativeUploadDir.replace(File.separator, "/") + "/" + fileName;
        }
        return null;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}