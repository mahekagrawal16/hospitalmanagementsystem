/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.docvisitnow.controller;

import com.docvisitnow.dao.PatientDAO;
import com.docvisitnow.model.Appointment;
import com.docvisitnow.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author mahekagrawal
 */
@WebServlet("/patient/dashboard")
public class PatientDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("homepage.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        int patientId = user.getUserId(); // From your logged-in user

        // DAO calls to fetch patient-specific data
        int totalAppointments = PatientDAO.getTotalAppointments(patientId);
        int upcomingAppointments = PatientDAO.getUpcomingAppointments(patientId);
        int pendingPrescriptions = PatientDAO.getPendingPrescriptions(patientId);
        boolean emergencyEnabled = PatientDAO.isEmergencyAccessEnabled(patientId);

        List<String> recentHospitals = PatientDAO.getRecentHospitals(patientId);
        List<String> recentHistory = PatientDAO.getRecentMedicalHistory(patientId);
        List<Appointment> lastThreeAppointments = PatientDAO.getLastThreeAppointments(patientId);
        Appointment nextAppointment = PatientDAO.getNextAppointment(patientId);
        System.out.println("PatientDashboardServlet: session full name = " + session.getAttribute("userFullName"));

        String fullName = (String) session.getAttribute("userFullName");
        request.setAttribute("userFullName", fullName);
        request.setAttribute("totalAppointments", totalAppointments);
        request.setAttribute("upcomingAppointments", upcomingAppointments);
        request.setAttribute("pendingPrescriptions", pendingPrescriptions);
        if (user != null) {
            String emergencyStatus = user.isEmergencyAccessEnabled() ? "Enabled" : "Disabled";
            request.setAttribute("emergencyStatus", emergencyStatus);
        }
        request.setAttribute("recentHospitals", recentHospitals);
        request.setAttribute("recentHistory", recentHistory);
        request.setAttribute("lastAppointments", lastThreeAppointments);
        request.setAttribute("nextAppointment", nextAppointment);

        request.getRequestDispatcher("/patient/dashboard.jsp").forward(request, response);
    }
}
