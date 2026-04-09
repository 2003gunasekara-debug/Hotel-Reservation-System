package com.HotelReservation.dao;

import com.HotelReservation.model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class AdminDAO {
    private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);
    private Connection con;

    public AdminDAO(Connection con) {
        this.con = con;
    }

    public Admin getAdminByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM dbo.admins WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            logger.debug("Admin found for email: {}", email);
            return new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullName"),
                    rs.getString("email"),
                    rs.getTimestamp("createdDate"),
                    rs.getTimestamp("lastLogin")
            );
        }
        logger.warn("No admin found for email: {}", email);
        return null;
    }

    public java.util.List<Admin> getAllAdmins() throws SQLException {
        java.util.List<Admin> admins = new java.util.ArrayList<>();
        String sql = "SELECT * FROM dbo.admins";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            admins.add(new Admin(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullName"),
                    rs.getString("email"),
                    rs.getTimestamp("createdDate"),
                    rs.getTimestamp("lastLogin")
            ));
        }
        return admins;
    }
}