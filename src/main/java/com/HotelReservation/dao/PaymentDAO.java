package com.HotelReservation.dao;

import com.HotelReservation.model.Payment;
import com.HotelReservation.util.DBUtil;
import java.sql.*;
import java.util.*;

public class PaymentDAO {
    public void addPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (tourist_id, room_id, amount, card_number, expiry, cvv, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, payment.getTouristId());
            ps.setInt(2, payment.getRoomId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getCardNumber());
            ps.setString(5, payment.getExpiry());
            ps.setString(6, payment.getCvv());
            ps.setTimestamp(7, new Timestamp(payment.getPaymentDate().getTime()));
            ps.executeUpdate();
        }
    }

    public List<Payment> getPaymentsByTourist(int touristId) throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE tourist_id=?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, touristId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getInt("id"));
                p.setTouristId(rs.getInt("tourist_id"));
                p.setRoomId(rs.getInt("room_id"));
                p.setAmount(rs.getDouble("amount"));
                p.setCardNumber(rs.getString("card_number"));
                p.setExpiry(rs.getString("expiry"));
                p.setCvv(rs.getString("cvv"));
                p.setPaymentDate(rs.getTimestamp("payment_date"));
                list.add(p);
            }
        }
        return list;
    }

    public void deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM payments WHERE id=?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        }
    }
}