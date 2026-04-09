package com.HotelReservation.servlets;

import com.HotelReservation.dao.PaymentDAO;
import com.HotelReservation.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class PaymentServlet extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer touristId = (Integer) session.getAttribute("touristId");
            if (touristId == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int roomId = Integer.parseInt(request.getParameter("roomId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String cardNumber = request.getParameter("cardNumber");
            String expiry = request.getParameter("expiry");
            String cvv = request.getParameter("cvv"); // Added cvv parameter

            Payment p = new Payment();
            p.setTouristId(touristId);
            p.setRoomId(roomId);
            p.setAmount(amount);
            p.setCardNumber(cardNumber);
            p.setExpiry(expiry);
            p.setCvv(cvv); // Set cvv value
            p.setPaymentDate(new java.util.Date());

            paymentDAO.addPayment(p);

            response.sendRedirect("ViewPaymentsServlet");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}