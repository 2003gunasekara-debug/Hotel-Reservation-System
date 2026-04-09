package com.HotelReservation.servlets;

import com.HotelReservation.dao.TouristDAO;
import com.HotelReservation.model.Tourist;
import com.HotelReservation.util.DBUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class TouristRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try(Connection con = DBUtil.getConnection()){
            TouristDAO dao = new TouristDAO(con);
            Tourist t = new Tourist(0,name,email,password);
            if(dao.register(t)){
                resp.sendRedirect("login.jsp?success=1");
            }else{
                resp.sendRedirect("touristRegister.jsp?error=1");
            }
        } catch(Exception e){ throw new ServletException(e); }
    }
}