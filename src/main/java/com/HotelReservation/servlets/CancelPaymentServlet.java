package com.HotelReservation.servlets;

import com.HotelReservation.dao.PaymentDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/CancelPaymentServlet")
public class CancelPaymentServlet extends HttpServlet {
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

            String paymentIdStr = request.getParameter("paymentId");
            if (paymentIdStr == null) {
                response.sendRedirect("ViewPaymentsServlet");
                return;
            }

            int paymentId = Integer.parseInt(paymentIdStr);
            paymentDAO.deletePayment(paymentId);

            request.setAttribute("message", "Payment money return within 5 days");
            request.getRequestDispatcher("ViewPaymentsServlet").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}