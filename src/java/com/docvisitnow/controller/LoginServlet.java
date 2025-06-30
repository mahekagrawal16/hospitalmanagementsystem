// File: LoginServlet.java
package com.docvisitnow.controller;

import com.docvisitnow.dao.UserDAO;
import com.docvisitnow.model.User;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String rawPassword = request.getParameter("password");
        String hashedPassword = null;

        try {
            hashedPassword = com.docvisitnow.util.PasswordUtil.hashPassword(rawPassword);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("homepage.html?loginError=1");
            return;
        }

        String role = request.getParameter("role");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(identifier, hashedPassword);

        if (user == null || !user.getRole().equalsIgnoreCase(role)) {
            response.sendRedirect("homepage.html?loginError=1");
            return;
        }
        if (user != null) {
            try {
                User fullUser = UserDAO.getEmergencyContactInfo(user.getUserId());
                if (fullUser != null) {
                    // Merge fetched fields into original user object
                    user.setEmergencyContactName(fullUser.getEmergencyContactName());
                    user.setEmergencyContactPhone(fullUser.getEmergencyContactPhone());
                    user.setBloodType(fullUser.getBloodType());
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userFullName", user.getFullName());

        switch (role.toLowerCase()) {
            case "patient":
                response.sendRedirect("patient/dashboard");
                break;
            case "doctor":
                response.sendRedirect("doctor/dashboard.html");
                break;
            case "admin":
                response.sendRedirect("admin/dashboard.html");
                break;
            case "hospital":
                response.sendRedirect("hospital/dashboard.html");
                break;
            default:
                response.sendRedirect("homepage.html");
        }
    }
}
