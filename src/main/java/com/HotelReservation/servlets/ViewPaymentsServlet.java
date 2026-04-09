package com.HotelReservation.servlets;

import com.HotelReservation.dao.PaymentDAO;
import com.HotelReservation.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ViewPaymentsServlet extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer touristId = (Integer) session.getAttribute("touristId");
            if (touristId == null) {
                response.sendRedirect("touristDashboard.jsp");
                return;
            }

            List<Payment> payments = paymentDAO.getPaymentsByTourist(touristId);
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("touristPayments.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
