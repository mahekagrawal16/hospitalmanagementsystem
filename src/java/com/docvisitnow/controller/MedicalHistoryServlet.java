/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.docvisitnow.controller;

import com.docvisitnow.dao.PatientDAO;
import com.docvisitnow.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mahekagrawal
 */
@WebServlet("/patient/medicalhistory")
public class MedicalHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/homepage.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        int patientId = user.getUserId();
        Map<String, List<String>> history = PatientDAO.getMedicalHistory(patientId);

        request.setAttribute("userFullName", user.getFullName());
        request.setAttribute("conditions", history.get("condition"));
        request.setAttribute("surgeries", history.get("surgery"));
        request.setAttribute("allergies", history.get("allergy"));
        request.setAttribute("medications", history.get("medication"));

        request.getRequestDispatcher("/patient/medicalhistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        int patientId = user.getUserId();

        String type = request.getParameter("type"); // e.g., "condition"
        String action = request.getParameter("action"); // "add", "edit", "delete"
        String value = request.getParameter("value"); // value or "old||new"

        try {
            switch (action) {
                case "add":
                    PatientDAO.insertMedicalHistory(patientId, type, value);
                    break;
                case "delete":
                    PatientDAO.deleteMedicalHistory(patientId, type, value);
                    break;
                case "edit":
                    String[] parts = value.split("\\|\\|");
                    if (parts.length == 2) {
                        PatientDAO.updateMedicalHistory(patientId, type, parts[0], parts[1]);
                    }
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
            }

        response.sendRedirect(request.getContextPath() + "/patient/medicalhistory");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
