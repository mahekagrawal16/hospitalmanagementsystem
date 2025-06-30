package com.docvisitnow.controller;

import com.docvisitnow.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.docvisitnow.model.User;

@WebServlet("/patient/bookappointment")
public class BookAppointmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get logged-in user session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("homepage.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("userFullName", user.getFullName());
        int patientId = user.getUserId();

        // Read form data
        int hospitalId = Integer.parseInt(request.getParameter("hospitalId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String reason = request.getParameter("reason");

        // Optional: add validation logic here
        String sql = "INSERT INTO appointments (patient_id, doctor_id, hospital_id, appointment_date, appointment_time, reason, contact_number, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setInt(3, hospitalId);
            ps.setString(4, appointmentDate);
            ps.setString(5, appointmentTime);
            ps.setString(6, reason);
            ps.setString(7, user.getPhoneNumber()); // assuming it's in session
            ps.setString(8, "Upcoming"); // default status

            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.sendRedirect(request.getContextPath() + "/patient/bookappointment.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/patient/bookappointment.jsp?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/patient/bookappointment.jsp?error=1");
        }
    }
}
