/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docvisitnow.dao;

import com.docvisitnow.model.Appointment;
import com.docvisitnow.util.DBConnection;
import java.sql.*;
/**
 *
 * @author mahekagrawal
 */
public class AppointmentDAO {
    public static boolean bookAppointment(Appointment appointment) {
    String sql = "INSERT INTO appointments (patient_id, doctor_id, hospital_id, appointment_date, appointment_time, reason, status) VALUES (?, ?, ?, ?, ?, ?, 'Scheduled')";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, appointment.getPatientId());
        ps.setInt(2, appointment.getDoctorId());
        ps.setInt(3, appointment.getHospitalId());
        ps.setDate(4, appointment.getAppointmentDate());
        ps.setTime(5, appointment.getAppointmentTime());
        ps.setString(6, appointment.getReason());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}
