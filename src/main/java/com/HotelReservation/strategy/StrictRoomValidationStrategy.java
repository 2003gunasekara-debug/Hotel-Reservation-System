package com.HotelReservation.strategy;

import com.HotelReservation.dao.RoomDAO;
import com.HotelReservation.model.Room;

public class StrictRoomValidationStrategy implements RoomValidationStrategy {
    @Override
    public String validate(int hotelId, String roomNumber, String roomType, double price, RoomDAO roomDAO) {
        // Standard validations
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            return "Room number is required";
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            return "Room type is required";
        }
        if (price <= 0) {
            return "Price must be greater than 0";
        }
        if (price > 10000) {
            return "Price cannot exceed $10,000";
        }

        // Additional strict validations
        if (!isValidRoomType(roomType)) {
            return "Invalid room type. Must be one of: " + getValidRoomTypes();
        }
        if (roomNumber.length() > 10) {
            return "Room number cannot exceed 10 characters";
        }
        try {
            if (roomDAO.isRoomNumberExists(hotelId, roomNumber, 0)) {
                return "Room number already exists";
            }
        } catch (Exception e) {
            return "Error validating room number";
        }
        return null;
    }

    private boolean isValidRoomType(String roomType) {
        return Room.TYPE_STANDARD.equals(roomType) ||
                Room.TYPE_DELUXE.equals(roomType) ||
                Room.TYPE_SUITE.equals(roomType) ||
                Room.TYPE_EXECUTIVE.equals(roomType) ||
                Room.TYPE_FAMILY.equals(roomType) ||
                Room.TYPE_PRESIDENTIAL.equals(roomType);
    }

    private String getValidRoomTypes() {
        return String.join(", ", Room.TYPE_STANDARD, Room.TYPE_DELUXE, Room.TYPE_SUITE,
                Room.TYPE_EXECUTIVE, Room.TYPE_FAMILY, Room.TYPE_PRESIDENTIAL);
    }
}