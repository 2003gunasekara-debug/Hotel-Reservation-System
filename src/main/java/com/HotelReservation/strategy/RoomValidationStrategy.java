package com.HotelReservation.strategy;

import com.HotelReservation.dao.RoomDAO;

public interface RoomValidationStrategy {
    String validate(int hotelId, String roomNumber, String roomType, double price, RoomDAO roomDAO);
}