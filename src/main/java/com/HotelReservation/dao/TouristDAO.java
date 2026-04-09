package com.HotelReservation.dao;

import com.HotelReservation.model.Tourist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TouristDAO {
    private Connection con;
    public TouristDAO(Connection con) { this.con = con; }

    public boolean register(Tourist t) throws SQLException {
        String sql = "INSERT INTO tourists (name, email, password) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getName());
        ps.setString(2, t.getEmail());
        ps.setString(3, t.getPassword());
        return ps.executeUpdate() > 0;
    }

    public Tourist login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM tourists WHERE email = ? AND password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Tourist(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"));
        }
        return null;
    }

    public Tourist getById(int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM tourists WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Tourist(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"));
        }
        return null;
    }

    public boolean update(Tourist t) throws SQLException {
        String sql = "UPDATE tourists SET name = ?, email = ?, password = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, t.getName());
        ps.setString(2, t.getEmail());
        ps.setString(3, t.getPassword());
        ps.setInt(4, t.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM tourists WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public List<Tourist> getAllTourists() throws SQLException {
        List<Tourist> tourists = new ArrayList<>();
        String sql = "SELECT * FROM tourists";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tourists.add(new Tourist(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
            ));
        }
        return tourists;
    }
}