package com.HotelReservation.dao;

import com.HotelReservation.model.Owner;
import com.HotelReservation.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {
    private static final Logger logger = LoggerFactory.getLogger(OwnerDAO.class);
    private Connection externalConn;
    private static volatile OwnerDAO instance = null;

    // Private constructor to prevent instantiation
    private OwnerDAO() {
        // Prevent instantiation via reflection
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    // Public method to provide access to the singleton instance
    public static OwnerDAO getInstance() {
        if (instance == null) {
            synchronized (OwnerDAO.class) {
                if (instance == null) {
                    instance = new OwnerDAO();
                }
            }
        }
        return instance;
    }

    // Constructor for external connection (used in specific cases, e.g., transactions)
    public OwnerDAO(Connection conn) {
        this.externalConn = conn;
    }

    public Owner getOwnerById(int id) throws SQLException {
        String sql = "SELECT * FROM owners WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Owner found for id: {}", id);
                    return new Owner(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"));
                }
                logger.warn("No owner found for id: {}", id);
                return null;
            }
        } finally {
            closeIfInternal(conn);
        }
    }

    public Owner getOwnerByUsername(String email) throws SQLException {
        String sql = "SELECT * FROM owners WHERE email = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Owner found for email: {}", email);
                    return new Owner(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone")
                    );
                }
                logger.warn("No owner found for email: {}", email);
                return null;
            }
        } finally {
            closeIfInternal(conn);
        }
    }

    public boolean updateOwner(Owner o) throws SQLException {
        String sql = "UPDATE owners SET name=?, email=?, password=?, phone=? WHERE id=?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getName());
            ps.setString(2, o.getEmail());
            ps.setString(3, o.getPassword());
            ps.setString(4, o.getPhone());
            ps.setInt(5, o.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Owner updated successfully for id: {}", o.getId());
                return true;
            }
            logger.warn("No rows affected when updating owner for id: {}", o.getId());
            return false;
        } finally {
            closeIfInternal(conn);
        }
    }

    public boolean deleteOwner(int id) throws SQLException {
        String deleteRooms = "DELETE FROM rooms WHERE hotel_id IN (SELECT id FROM hotels WHERE owner_id = ?)";
        String deleteHotels = "DELETE FROM hotels WHERE owner_id = ?";
        String deleteOwner = "DELETE FROM owners WHERE id=?";
        Connection conn = getConnection();
        boolean originalAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false); // Start transaction

        try {
            try (PreparedStatement psRooms = conn.prepareStatement(deleteRooms)) {
                psRooms.setInt(1, id);
                int roomsDeleted = psRooms.executeUpdate();
                logger.debug("Deleted {} rooms for ownerId: {}", roomsDeleted, id);
            }

            try (PreparedStatement psHotels = conn.prepareStatement(deleteHotels)) {
                psHotels.setInt(1, id);
                int hotelsDeleted = psHotels.executeUpdate();
                logger.debug("Deleted {} hotels for ownerId: {}", hotelsDeleted, id);
            }

            try (PreparedStatement psOwner = conn.prepareStatement(deleteOwner)) {
                psOwner.setInt(1, id);
                int rowsAffected = psOwner.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Owner deleted successfully for ownerId: {}", id);
                    conn.commit();
                    return true;
                } else {
                    logger.warn("No owner found to delete for ownerId: {}", id);
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            conn.rollback();
            logger.error("Error deleting owner for ownerId: {}", id, e);
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
            closeIfInternal(conn);
        }
    }

    public List<Owner> getAllOwners() throws SQLException {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owners";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                owners.add(new Owner(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone")
                ));
            }
        } finally {
            closeIfInternal(conn);
        }
        return owners;
    }

    public boolean registerOwner(Owner owner) throws SQLException {
        String sql = "INSERT INTO owners (name, email, password, phone) VALUES (?, ?, ?, ?)";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, owner.getName());
            ps.setString(2, owner.getEmail());
            ps.setString(3, owner.getPassword());
            ps.setString(4, owner.getPhone());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Owner registered successfully: {}", owner.getEmail());
                return true;
            }
            logger.warn("Failed to register owner: {}", owner.getEmail());
            return false;
        } finally {
            closeIfInternal(conn);
        }
    }

    private Connection getConnection() throws SQLException {
        return (externalConn != null) ? externalConn : DBUtil.getConnection();
    }

    private void closeIfInternal(Connection conn) {
        if (externalConn == null && conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
    }
}