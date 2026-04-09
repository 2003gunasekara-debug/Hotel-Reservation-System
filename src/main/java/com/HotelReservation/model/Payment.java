package com.HotelReservation.model;

import java.util.Date;

public class Payment {
    private int id;
    private int touristId;
    private int roomId;
    private double amount;
    private String cardNumber;
    private String expiry;
    private String cvv; // Added cvv field
    private Date paymentDate;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTouristId() { return touristId; }
    public void setTouristId(int touristId) { this.touristId = touristId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
}