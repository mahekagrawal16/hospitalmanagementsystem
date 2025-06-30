/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.docvisitnow.controller;

import com.docvisitnow.dao.UserDAO;
import com.docvisitnow.model.User;
import com.docvisitnow.util.PasswordUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mahekagrawal
 */
public class ProfileSettingsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("../homepage.html");
                return;
            }

            User user = (User) session.getAttribute("user");
            int userId = user.getUserId();
            request.setAttribute("userFullName", user.getFullName());
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            boolean emailNotif = request.getParameter("emailNotifications") != null;
            String dobParam = request.getParameter("dob");
            java.sql.Date dob = null;

            if (dobParam != null && !dobParam.isEmpty()) {
                try {
                    dob = java.sql.Date.valueOf(dobParam); // Converts from "yyyy-MM-dd"
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // log the problem
                }
            }

            user.setDob(dob);
            UserDAO.updateUserDOB(userId, dob); // You’ll need this method
            String newPassword = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

// Only update password if both fields are non-empty
            if (newPassword != null && !newPassword.isEmpty()) {
                if (newPassword.equals(confirmPassword)) {
                    try {
                        String hashedPassword = PasswordUtil.hashPassword(newPassword);
                        UserDAO.updatePassword(userId, hashedPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendRedirect("patient/profile-settings.jsp?error=passwordUpdateFailed");
                        return;
                    }
                } else {
                    response.sendRedirect("patient/profile-settings.jsp?error=passwordMismatch");
                    return;
                }
            }

            try {
                UserDAO.updateUserProfile(userId, phone, address, emailNotif);
                user.setPhoneNumber(phone);
                user.setAddress(address);
                user.setEmailNotifications(emailNotif);
                session.setAttribute("user", user);
                response.sendRedirect("patient/profile-settings.jsp?success=1");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("patient/profile-settings.jsp?error=1");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfileSettingsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
