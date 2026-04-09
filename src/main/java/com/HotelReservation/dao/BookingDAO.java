package com.HotelReservation.dao;

import com.HotelReservation.model.Booking;
import com.HotelReservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection externalConn;

    public BookingDAO() {}
    public BookingDAO(Connection conn) {
        this.externalConn = conn;
    }

    // addBooking - FIXED Connection Management
    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (tourist_id, room_id, check_in, check_out, status, guests) VALUES (?, ?, ?, ?, ?, ?)";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getTouristId());
            ps.setInt(2, booking.getRoomId());
            ps.setDate(3, java.sql.Date.valueOf(booking.getCheckIn()));
            ps.setDate(4, java.sql.Date.valueOf(booking.getCheckOut()));
            ps.setString(5, booking.getStatus());
            ps.setInt(6, booking.getGuests());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert booking, no rows affected.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    booking.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in addBooking: Error Code=" + e.getErrorCode() + ", SQL State=" + e.getSQLState());
            throw e;
        } finally {
            closeIfInternal(con);
        }
    }

    // updateBooking - FIXED Connection Management
    public void updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE bookings SET check_in=?, check_out=?, status=?, guests=? WHERE id=?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(booking.getCheckIn()));
            ps.setDate(2, java.sql.Date.valueOf(booking.getCheckOut()));
            ps.setString(3, booking.getStatus());
            ps.setInt(4, booking.getGuests());
            ps.setInt(5, booking.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update booking, no rows affected.");
            }
        } finally {
            closeIfInternal(con);
        }
    }

    // deleteBooking - FIXED Connection Management
    public void deleteBooking(int id) throws SQLException {
        String sql = "DELETE FROM bookings WHERE id=?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to delete booking, no rows affected.");
            }
        } finally {
            closeIfInternal(con);
        }
    }

    // getBookingById - FIXED Connection Management
    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT b.*, t.name AS guest_name, r.room_type "
                + "FROM bookings b "
                + "JOIN tourists t ON b.tourist_id = t.id "
                + "JOIN rooms r ON b.room_id = r.id "
                + "WHERE b.id=?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setTouristId(rs.getInt("tourist_id"));
                    b.setRoomId(rs.getInt("room_id"));
                    b.setCheckIn(rs.getDate("check_in").toLocalDate());
                    b.setCheckOut(rs.getDate("check_out").toLocalDate());
                    b.setStatus(rs.getString("status"));
                    b.setGuests(rs.getInt("guests"));
                    b.setGuestName(rs.getString("guest_name"));
                    b.setRoomType(rs.getString("room_type"));
                    return b;
                }
            }
        } finally {
            closeIfInternal(con);
        }
        return null;
    }

    // getBookingsByRoom - FIXED Connection Management
    public List<Booking> getBookingsByRoom(int roomId) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, t.name AS guest_name, r.room_type "
                + "FROM bookings b "
                + "JOIN tourists t ON b.tourist_id = t.id "
                + "JOIN rooms r ON b.room_id = r.id "
                + "WHERE b.room_id=?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setTouristId(rs.getInt("tourist_id"));
                    b.setRoomId(rs.getInt("room_id"));
                    b.setCheckIn(rs.getDate("check_in").toLocalDate());
                    b.setCheckOut(rs.getDate("check_out").toLocalDate());
                    b.setStatus(rs.getString("status"));
                    b.setGuests(rs.getInt("guests"));
                    b.setGuestName(rs.getString("guest_name"));
                    b.setRoomType(rs.getString("room_type"));
                    list.add(b);
                }
            }
        } finally {
            closeIfInternal(con);
        }
        return list;
    }

    // getBookingsByTourist - FIXED Connection Management
    public List<Booking> getBookingsByTourist(int touristId) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, t.name AS guest_name, r.room_type "
                + "FROM bookings b "
                + "JOIN tourists t ON b.tourist_id = t.id "
                + "JOIN rooms r ON b.room_id = r.id "
                + "WHERE b.tourist_id=?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, touristId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setTouristId(rs.getInt("tourist_id"));
                    b.setRoomId(rs.getInt("room_id"));
                    b.setCheckIn(rs.getDate("check_in").toLocalDate());
                    b.setCheckOut(rs.getDate("check_out").toLocalDate());
                    b.setStatus(rs.getString("status"));
                    b.setGuests(rs.getInt("guests"));
                    b.setGuestName(rs.getString("guest_name"));
                    b.setRoomType(rs.getString("room_type"));
                    list.add(b);
                }
            }
        } finally {
            closeIfInternal(con);
        }
        return list;
    }

    // getBookingsByHotel - FIXED Connection Management
    public List<Booking> getBookingsByHotel(int hotelId) throws SQLException {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT b.*, t.name AS guest_name, r.room_type "
                + "FROM bookings b "
                + "JOIN rooms r ON b.room_id = r.id "
                + "JOIN tourists t ON b.tourist_id = t.id "
                + "WHERE r.hotel_id = ?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hotelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setTouristId(rs.getInt("tourist_id"));
                    b.setRoomId(rs.getInt("room_id"));
                    b.setCheckIn(rs.getDate("check_in").toLocalDate());
                    b.setCheckOut(rs.getDate("check_out").toLocalDate());
                    b.setStatus(rs.getString("status"));
                    b.setGuests(rs.getInt("guests"));
                    b.setGuestName(rs.getString("guest_name"));
                    b.setRoomType(rs.getString("room_type"));
                    list.add(b);
                }
            }
        } finally {
            closeIfInternal(con);
        }
        return list;
    }

    // getBookingsByTouristId - NEW METHOD with proper connection management
    public List<Booking> getBookingsByTouristId(int touristId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, t.name AS guest_name, r.room_type "
                + "FROM bookings b "
                + "JOIN tourists t ON b.tourist_id = t.id "
                + "JOIN rooms r ON b.room_id = r.id "
                + "WHERE b.tourist_id = ?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, touristId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setTouristId(rs.getInt("tourist_id"));
                    booking.setRoomId(rs.getInt("room_id"));
                    booking.setCheckIn(rs.getDate("check_in").toLocalDate());
                    booking.setCheckOut(rs.getDate("check_out").toLocalDate());
                    booking.setGuests(rs.getInt("guests"));
                    booking.setStatus(rs.getString("status"));
                    booking.setGuestName(rs.getString("guest_name"));
                    booking.setRoomType(rs.getString("room_type"));
                    bookings.add(booking);
                }
            }
        } finally {
            closeIfInternal(con);
        }
        return bookings;
    }

    // NEW METHOD: Check for active bookings for a room
    public boolean hasActiveBookings(int roomId) throws SQLException {
        String sql = "SELECT 1 FROM bookings "
                + "WHERE room_id = ? AND status IN ('Pending', 'Confirmed') "
                + "AND check_out >= ?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } finally {
            closeIfInternal(con);
        }
    }

    // touristExists - FIXED Connection Management
    public boolean touristExists(int touristId) throws SQLException {
        String sql = "SELECT 1 FROM tourists WHERE id = ?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, touristId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } finally {
            closeIfInternal(con);
        }
    }

    // roomExists - FIXED Connection Management
    public boolean roomExists(int roomId) throws SQLException {
        String sql = "SELECT 1 FROM rooms WHERE id = ?";
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } finally {
            closeIfInternal(con);
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