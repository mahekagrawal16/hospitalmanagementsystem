// File: MyAppointmentsServlet.java
package com.docvisitnow.controller;

import com.docvisitnow.dao.PatientDAO;
import com.docvisitnow.model.Appointment;
import com.docvisitnow.model.User;
import com.docvisitnow.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/patient/myappointment")
public class MyAppointmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/homepage.html");
            return;
        }

        User user = (User) session.getAttribute("user");
        int patientId = user.getUserId();
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT a.appointment_id, a.appointment_date, a.appointment_time, a.status, "
                    + "d.full_name AS doctor_name, h.name AS hospital_name "
                    + "FROM appointments a "
                    + "JOIN users d ON a.doctor_id = d.user_id "
                    + "JOIN hospitals h ON a.hospital_id = h.hospital_id "
                    + "WHERE a.patient_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, patientId);
                try (ResultSet rs = ps.executeQuery()) {
                    boolean hasRows = false;
                    while (rs.next()) {
                        hasRows = true;
                        Appointment appt = new Appointment();
                        appt.setAppointmentId(rs.getInt("appointment_id"));
                        appt.setAppointmentDate(rs.getDate("appointment_date"));
                        appt.setAppointmentTime(rs.getTime("appointment_time"));
                        appt.setStatus(rs.getString("status"));
                        appt.setDoctorName(rs.getString("doctor_name"));
                        appt.setHospitalName(rs.getString("hospital_name"));
                        appointments.add(appt);
                    }

                    if (!hasRows) {
                        System.out.println("No appointments found for this patient.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("SQL Error: " + e.getMessage());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("userFullName", user.getFullName());
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/patient/myappointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("reschedule".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            String newDate = request.getParameter("newDate");
            String newTime = request.getParameter("newTime");

            // 🔁 Update in DB
            PatientDAO.rescheduleAppointment(appointmentId, newDate, newTime);

        } else if ("cancel".equals(action)) {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            String reason = request.getParameter("reason");

            // ❌ Mark as cancelled
            PatientDAO.cancelAppointment(appointmentId, reason);
        }

        response.sendRedirect("myappointment"); // Refresh the page
    }

}
