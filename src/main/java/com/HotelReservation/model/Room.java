package com.HotelReservation.model;

public class Room {
    private int id;
    private int hotelId;
    private String roomNumber;
    private String roomType;
    private double price;
    private String imagePath;
    private String amenities;

    // Constants for room types
    public static final String TYPE_STANDARD = "Standard";
    public static final String TYPE_DELUXE = "Deluxe";
    public static final String TYPE_SUITE = "Suite";
    public static final String TYPE_EXECUTIVE = "Executive";
    public static final String TYPE_FAMILY = "Family";
    public static final String TYPE_PRESIDENTIAL = "Presidential";

    // Default constructor
    public Room() {}

    // Constructor with all fields
    public Room(int id, int hotelId, String roomNumber, String roomType, double price, String imagePath, String amenities) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber != null ? roomNumber : "";
        this.roomType = roomType != null ? roomType : "";
        this.price = price;
        this.imagePath = imagePath != null ? imagePath : "uploads/default_room.jpg";
        this.amenities = amenities != null ? amenities : "";
    }

    // Constructor without id (for new rooms before saving to database)
    public Room(int hotelId, String roomNumber, String roomType, double price, String imagePath, String amenities) {
        this.hotelId = hotelId;
        this.roomNumber = roomNumber != null ? roomNumber : "";
        this.roomType = roomType != null ? roomType : "";
        this.price = price;
        this.imagePath = imagePath != null ? imagePath : "uploads/default_room.jpg";
        this.amenities = amenities != null ? amenities : "";
    }

    // Getters
    public int getId() { return id; }
    public int getHotelId() { return hotelId; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public String getAmenities() { return amenities; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber != null ? roomNumber : ""; }
    public void setRoomType(String roomType) { this.roomType = roomType != null ? roomType : ""; }
    public void setPrice(double price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath != null ? imagePath : "uploads/default_room.jpg"; }
    public void setAmenities(String amenities) { this.amenities = amenities != null ? amenities : ""; }

    // Utility methods
    public String[] getAmenityTags() {
        return amenities != null && !amenities.isEmpty() ? amenities.split(",") : new String[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (hotelId != room.hotelId) return false;
        if (Double.compare(room.price, price) != 0) return false;
        if (!roomNumber.equals(room.roomNumber)) return false;
        if (!roomType.equals(room.roomType)) return false;
        if (!imagePath.equals(room.imagePath)) return false;
        return amenities.equals(room.amenities);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + hotelId;
        result = 31 * result + roomNumber.hashCode();
        result = 31 * result + roomType.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + imagePath.hashCode();
        result = 31 * result + amenities.hashCode();
        return result;
    }

    // Builder pattern for fluent object creation
    public static class Builder {
        private int id;
        private int hotelId;
        private String roomNumber;
        private String roomType;
        private double price;
        private String imagePath = "uploads/default_room.jpg";
        private String amenities = "";

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder hotelId(int hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder roomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder roomType(String roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder amenities(String amenities) {
            this.amenities = amenities;
            return this;
        }

        public Room build() {
            return new Room(id, hotelId, roomNumber, roomType, price, imagePath, amenities);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}