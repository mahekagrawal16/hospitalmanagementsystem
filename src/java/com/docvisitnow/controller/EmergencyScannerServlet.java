package com.docvisitnow.controller;

import java.sql.*;
import com.docvisitnow.dao.UserDAO;
import com.docvisitnow.model.User;
import com.docvisitnow.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.List;


@MultipartConfig
public class EmergencyScannerServlet extends HttpServlet {
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("faceImage");
        String fileName = filePart.getSubmittedFileName();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT full_name, blood_type, emergency_contact_name, emergency_contact_phone FROM users WHERE profile_image_path LIKE ?");
            ps.setString(1, "%" + fileName); // match by filename

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("fullName", rs.getString("full_name"));
                request.setAttribute("bloodType", rs.getString("blood_type"));
                request.setAttribute("emergencyContact", rs.getString("emergency_contact_phone") + " (" + rs.getString("emergency_contact_name") + ")");
                request.getRequestDispatcher("/emergencyscanner.jsp?match=1").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

                request.getRequestDispatcher("/emergencyscanner.jsp?match=0").forward(request, response);
    }
}

