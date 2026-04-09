package com.HotelReservation.dao;

import com.HotelReservation.model.Review;
import com.HotelReservation.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private Connection externalConn;

    public ReviewDAO() {}
    public ReviewDAO(Connection conn) {
        this.externalConn = conn;
    }

    public void addReview(Review review) throws SQLException {
        String sql = "INSERT INTO reviews (tourist_id, hotel_id, booking_id, rating, description, image_path, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, review.getTouristId());
            ps.setInt(2, review.getHotelId());
            ps.setInt(3, review.getBookingId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getDescription());
            ps.setString(6, review.getImagePath());
            ps.setTimestamp(7, Timestamp.valueOf(review.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    review.setId(keys.getInt(1));
                }
            }
        } finally {
            closeIfInternal(conn);
        }
    }

    public List<Review> getReviewsByTouristId(int touristId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, h.name AS hotel_name FROM reviews r JOIN hotels h ON r.hotel_id = h.id WHERE r.tourist_id = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, touristId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setTouristId(rs.getInt("tourist_id"));
                    review.setHotelId(rs.getInt("hotel_id"));
                    review.setBookingId(rs.getInt("booking_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setDescription(rs.getString("description"));
                    review.setImagePath(rs.getString("image_path"));
                    review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    review.setHotelName(rs.getString("hotel_name"));
                    reviews.add(review);
                }
            }
        } finally {
            closeIfInternal(conn);
        }
        return reviews;
    }

    public List<Review> getReviewsByHotelId(int hotelId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, t.name AS tourist_name FROM reviews r JOIN tourists t ON r.tourist_id = t.id WHERE r.hotel_id = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setTouristId(rs.getInt("tourist_id"));
                    review.setHotelId(rs.getInt("hotel_id"));
                    review.setBookingId(rs.getInt("booking_id"));
                    review.setRating(rs.getInt("rating"));
                    review.setDescription(rs.getString("description"));
                    review.setImagePath(rs.getString("image_path"));
                    review.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    review.setTouristName(rs.getString("tourist_name"));
                    reviews.add(review);
                }
            }
        } finally {
            closeIfInternal(conn);
        }
        return reviews;
    }

    public void deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM reviews WHERE id = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reviewId);
            ps.executeUpdate();
        } finally {
            closeIfInternal(conn);
        }
    }

    private Connection getConnection() throws SQLException {
        return (externalConn != null) ? externalConn : DBUtil.getConnection();
    }

    private void closeIfInternal(Connection conn) {
        if (externalConn == null && conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}