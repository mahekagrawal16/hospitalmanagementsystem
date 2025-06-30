package com.docvisitnow.controller;

import com.docvisitnow.dao.UserDAO;
import com.docvisitnow.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class EmergencyContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("homepage.html");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        int userId = sessionUser.getUserId();

        String name = request.getParameter("emergencyName");
        String phone = request.getParameter("emergencyPhone");

        try {
            // Update emergency contact in DB
            UserDAO.updateEmergencyContact(userId, name, phone);

            // Fetch the updated user from DB to get fresh data like blood_type, etc.
            User updatedUser = UserDAO.getUserById(userId); // You must implement this method if not done
            request.setAttribute("userFullName", sessionUser.getFullName());
            // Update session
            session.setAttribute("user", updatedUser);

            // Redirect to emergency page with success flag
            response.sendRedirect("patient/emergency.jsp?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("patient/emergency.jsp?error=1");
        }
    }
}
