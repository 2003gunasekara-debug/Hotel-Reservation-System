package com.HotelReservation.model;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.util.DBUtil;

import java.sql.SQLException;
import java.time.LocalDate;

public class Availability {
    private int id;
    private int roomId;
    private LocalDate date;
    private boolean available;

    public Availability(int id, int roomId, LocalDate date, boolean available) {
        this.id = id;
        this.roomId = roomId;
        this.date = date;
        this.available = available;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // Enhanced getRoomType to fetch from RoomDAO
    public String getRoomType() {
        try {
            RoomDAO roomDAO = new RoomDAO();
            return roomDAO.getRoomById(this.roomId).getRoomType();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown"; // Fallback value
        }
    }
}