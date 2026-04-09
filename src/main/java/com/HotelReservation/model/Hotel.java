package com.HotelReservation.model;

public class Hotel {
    private int id;
    private int ownerId;
    private String name;
    private String location;
    private String description;
    private String imagePath;
    private String amenities;

    public Hotel() {
        this.name = "";
        this.location = "";
        this.description = "";
        this.imagePath = "";
        this.amenities = "";
    }

    public Hotel(int id, int ownerId, String name, String location,
                 String description, String imagePath, String amenities) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = (name != null) ? name : "";
        this.location = (location != null) ? location : "";
        this.description = (description != null) ? description : "";
        this.imagePath = (imagePath != null) ? imagePath : "";
        this.amenities = (amenities != null) ? amenities : "";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = (name != null) ? name : ""; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = (location != null) ? location : ""; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = (description != null) ? description : ""; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = (imagePath != null) ? imagePath : ""; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = (amenities != null) ? amenities : ""; }
}