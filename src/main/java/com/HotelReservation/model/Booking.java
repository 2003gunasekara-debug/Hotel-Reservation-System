package com.HotelReservation.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int roomId;
    private int touristId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status; // e.g., "Pending", "Confirmed", "Cancelled"
    private String guestName; // Added for display purposes
    private String roomType;  // Added for display purposes
    private int guests; // Added for number of guests

    public Booking() {}

    public Booking(int id, int roomId, int touristId, LocalDate checkIn, LocalDate checkOut, String status, int guests) {
        this.id = id;
        this.roomId = roomId;
        this.touristId = touristId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.guests = guests;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getTouristId() { return touristId; }
    public void setTouristId(int touristId) { this.touristId = touristId; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }
}