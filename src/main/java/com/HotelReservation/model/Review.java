package com.HotelReservation.model;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int touristId;
    private int hotelId;
    private int bookingId;
    private int rating;
    private String description;
    private String imagePath;
    private LocalDateTime createdAt;
    private String hotelName;
    private String touristName;

    private static final String DEFAULT_IMAGE_PATH = "Uploads/reviews/default.jpg"; // Consistent default path

    // Constructors
    public Review() {
        this.imagePath = DEFAULT_IMAGE_PATH;
    }

    public Review(int id, int touristId, int hotelId, int bookingId, int rating, String description, String imagePath, LocalDateTime createdAt) {
        this.id = id;
        this.touristId = touristId;
        this.hotelId = hotelId;
        this.bookingId = bookingId;
        this.rating = rating;
        this.description = description;
        setImagePath(imagePath);
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTouristId() { return touristId; }
    public void setTouristId(int touristId) { this.touristId = touristId; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) {
        if (imagePath != null && !imagePath.trim().isEmpty() && imagePath.length() <= 255) {
            this.imagePath = imagePath.trim().replace('\\', '/');
        } else {
            this.imagePath = DEFAULT_IMAGE_PATH;
        }
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getTouristName() { return touristName; }
    public void setTouristName(String touristName) { this.touristName = touristName; }
}