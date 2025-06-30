package com.docvisitnow.controller;

import com.docvisitnow.dao.PatientDAO;
import com.docvisitnow.model.Prescription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/patient/prescription")
public class PrescriptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // do not create new
        if (session == null || session.getAttribute("userId") == null) {
            System.out.println("Redirecting because session is null or userId is missing.");
            response.sendRedirect(request.getContextPath() + "/homepage.html");
            return;
        }

        int patientId = (int) session.getAttribute("userId"); // This should now work
        System.out.println("Logged-in patient ID: " + patientId);

        List<Prescription> prescriptions = PatientDAO.getPrescriptions(patientId);
        request.setAttribute("prescriptions", prescriptions);

        request.getRequestDispatcher("/patient/prescription.jsp").forward(request, response);
    }
}
