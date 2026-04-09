package com.HotelReservation.dao;

import com.HotelReservation.model.Hotel;
import com.HotelReservation.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private Connection externalConn;

    // Constructors
    public HotelDAO() {}
    public HotelDAO(Connection conn) {
        this.externalConn = conn;
    }

    // Add hotel
    public void addHotel(Hotel hotel) throws SQLException {
        String sql = "INSERT INTO hotels (owner_id, name, location, description, image_path, amenities) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotel.getOwnerId());
            ps.setString(2, hotel.getName());
            ps.setString(3, hotel.getLocation());
            ps.setString(4, hotel.getDescription());
            ps.setString(5, hotel.getImagePath());
            ps.setString(6, hotel.getAmenities());
            ps.executeUpdate();
        } finally {
            closeIfInternal(conn);
        }
    }

    // Update hotel
    public void updateHotel(Hotel hotel) throws SQLException {
        String sql = "UPDATE hotels SET name=?, location=?, description=?, image_path=?, amenities=? WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getLocation());
            ps.setString(3, hotel.getDescription());
            ps.setString(4, hotel.getImagePath());
            ps.setString(5, hotel.getAmenities());
            ps.setInt(6, hotel.getId());
            ps.executeUpdate();
        } finally {
            closeIfInternal(conn);
        }
    }

    // DELETE HOTEL: FIXED with Transaction and Cascading Delete
    public void deleteHotel(int id) throws SQLException {
        Connection conn = getConnection();
        boolean originalAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false); // Start transaction

        try {
            // 1. Delete dependent rooms first
            deleteRoomsByHotel(conn, id);

            // 2. Delete the hotel
            String sql = "DELETE FROM hotels WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to delete hotel: ID not found.");
                }
            }

            conn.commit(); // Commit if both deletions succeeded

        } catch (SQLException e) {
            conn.rollback(); // Rollback on any failure
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit); // Restore original mode
            closeIfInternal(conn); // Only close if it was opened internally
        }
    }

    // Helper method to delete rooms (used inside the transaction)
    private void deleteRoomsByHotel(Connection conn, int hotelId) throws SQLException {
        // NOTE: This assumes rooms are children of hotels, but parents of bookings.
        // If rooms have dependent bookings, those must be deleted first here.

        // This method relies on RoomDAO's logic for rooms that might have bookings,
        // but since we only have HotelDAO and RoomDAO, we must assume Rooms have no children or handle them here.
        // For simplicity, we just delete the rooms. If rooms have foreign keys to bookings,
        // you might need a method to delete bookings first.

        // Assuming no children on rooms for simplicity, or that they cascade automatically.
        String sql = "DELETE FROM rooms WHERE hotel_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            ps.executeUpdate();
        }
    }

    // Get hotel by ID
    public Hotel getHotelById(int id) throws SQLException {
        String sql = "SELECT * FROM hotels WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractHotel(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving hotel with ID: " + id);
            e.printStackTrace();
            throw e;
        } finally {
            closeIfInternal(conn);
        }
        return null;
    }

    // Hotels by owner
    public List<Hotel> getHotelsByOwner(int ownerId) throws SQLException {
        List<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE owner_id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(extractHotel(rs));
            }
        } finally {
            closeIfInternal(conn);
        }
        return list;
    }

    // All hotels (for tourists)
    public List<Hotel> getAllHotels() throws SQLException {
        List<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM hotels";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(extractHotel(rs));
        } finally {
            closeIfInternal(conn);
        }
        return list;
    }

    private Hotel extractHotel(ResultSet rs) throws SQLException {
        return new Hotel(
                rs.getInt("id"),
                rs.getInt("owner_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getString("image_path"),
                rs.getString("amenities")
        );
    }

    private Connection getConnection() throws SQLException {
        return (externalConn != null) ? externalConn : DBUtil.getConnection();
    }

    // NEW HELPER METHOD TO CONTROL CLOSING
    private void closeIfInternal(Connection conn) {
        if (externalConn == null && conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}