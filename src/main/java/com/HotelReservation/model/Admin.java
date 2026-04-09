package com.HotelReservation.model;

import java.sql.Timestamp;

public class Admin {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Timestamp createdDate;
    private Timestamp lastLogin;

    public Admin(int id, String username, String password, String fullName, String email, Timestamp createdDate, Timestamp lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public Timestamp getCreatedDate() { return createdDate; }
    public Timestamp getLastLogin() { return lastLogin; }
}