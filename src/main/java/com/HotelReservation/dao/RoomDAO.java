package com.HotelReservation.dao;

import com.HotelReservation.model.Room;
import com.HotelReservation.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private Connection externalConn;

    // Constructors
    public RoomDAO() {}
    public RoomDAO(Connection conn) {
        this.externalConn = conn;
    }

    // Add a new room
    public void addRoom(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (hotel_id, room_number, room_type, price, image_path, amenities) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getRoomType());
            ps.setDouble(4, room.getPrice());
            ps.setString(5, room.getImagePath());
            ps.setString(6, room.getAmenities());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) room.setId(keys.getInt(1));
            }
        } finally {
            closeIfInternal(conn);
        }
    }

    // Update an existing room
    public void updateRoom(Room room) throws SQLException {
        String sql = "UPDATE rooms SET hotel_id=?, room_number=?, room_type=?, price=?, image_path=?, amenities=? WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getRoomType());
            ps.setDouble(4, room.getPrice());
            ps.setString(5, room.getImagePath());
            ps.setString(6, room.getAmenities());
            ps.setInt(7, room.getId());
            ps.executeUpdate();
        } finally {
            closeIfInternal(conn);
        }
    }

    // Delete a room
    public boolean deleteRoom(int id) throws SQLException {
        String sql = "DELETE FROM rooms WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            closeIfInternal(conn);
        }
    }

    // Get room by ID
    public Room getRoomById(int id) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractRoom(rs);
            }
        } finally {
            closeIfInternal(conn);
        }
        return null;
    }

    // Get rooms by hotel
    public List<Room> getRoomsByHotel(int hotelId) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) rooms.add(extractRoom(rs));
            }
        } finally {
            closeIfInternal(conn);
        }
        return rooms;
    }

    // Alias for servlet usage
    public List<Room> getRoomsByHotelId(int hotelId) throws SQLException {
        return getRoomsByHotel(hotelId);
    }

    // Check if a room number exists for a given hotel (exclude an ID when editing)
    public boolean isRoomNumberExists(int hotelId, String roomNumber, int excludeRoomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rooms WHERE hotel_id = ? AND room_number = ? AND id <> ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            ps.setString(2, roomNumber);
            ps.setInt(3, excludeRoomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } finally {
            closeIfInternal(conn);
        }
        return false;
    }

    // Helper to map result
    private Room extractRoom(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("id"),
                rs.getInt("hotel_id"),
                rs.getString("room_number"),
                rs.getString("room_type"),
                rs.getDouble("price"),
                rs.getString("image_path"),
                rs.getString("amenities")
        );
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