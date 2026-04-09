package com.HotelReservation.strategy;

import com.HotelReservation.dao.RoomDAO;

public class StandardRoomValidationStrategy implements RoomValidationStrategy {
    @Override
    public String validate(int hotelId, String roomNumber, String roomType, double price, RoomDAO roomDAO) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            return "Room number is required";
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            return "Room type is required";
        }
        if (price <= 0) {
            return "Price must be greater than 0";
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
}