package com.docvisitnow.controller;

import com.docvisitnow.dao.UserDAO;
import com.docvisitnow.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,     // 2MB
                 maxFileSize = 1024 * 1024 * 10,          // 10MB
                 maxRequestSize = 1024 * 1024 * 50)       // 50MB
public class UploadBiometricServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "biometrics";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("homepage.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();

        Part filePart = request.getPart("biometricImage");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Upload path on server
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String savedFilePath = uploadPath + File.separator + userId + "_" + fileName;
        filePart.write(savedFilePath);

        // Relative path to save in DB
        String relativePath = UPLOAD_DIRECTORY + "/" + userId + "_" + fileName;

        try {
            UserDAO.saveBiometricPath(userId, relativePath);

            // Refresh user session
            user.setProfileImagePath(relativePath);
            session.setAttribute("user", user);

            response.sendRedirect("patient/emergency.jsp?biometric=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("patient/emergency.jsp?biometric=error");
        }
    }
}
