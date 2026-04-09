package com.HotelReservation.dao;

import com.HotelReservation.model.Availability;
import com.HotelReservation.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityDAO {

    public void addAvailability(Availability availability) throws SQLException {
        String sql = "INSERT INTO availability_dates (room_id, date, available) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, availability.getRoomId());
            ps.setDate(2, Date.valueOf(availability.getDate()));
            ps.setBoolean(3, availability.isAvailable());
            ps.executeUpdate();
        }
    }

    public void updateAvailability(Availability availability) throws SQLException {
        String sql = "UPDATE availability_dates SET available = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, availability.isAvailable());
            ps.setInt(2, availability.getId());
            ps.executeUpdate();
        }
    }

    public List<Availability> getAvailabilityByHotel(int hotelId) throws SQLException {
        List<Availability> list = new ArrayList<>();
        String sql = "SELECT ad.*, r.room_type FROM availability_dates ad " +
                "JOIN rooms r ON ad.room_id = r.id WHERE r.hotel_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Availability a = new Availability(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getBoolean("available")
                );
                // Note: roomType can be set if needed
                list.add(a);
            }
        }
        return list;
    }
}