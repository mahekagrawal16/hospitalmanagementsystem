// File: DoctorDAO.java
package com.docvisitnow.dao;

import com.docvisitnow.model.DoctorDetails;
import com.docvisitnow.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DoctorDAO {
    public void insertDoctorDetails(int userId, DoctorDetails doctor) {
        String sql = "UPDATE users SET specialization = ?, license_number = ?, years_experience = ?, " +
                     "hospital_affiliation = ?, profile_image_path = ?, availability = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doctor.getSpecialization());
            ps.setString(2, doctor.getLicenseNumber());
            ps.setInt(3, doctor.getYearsExperience());
            ps.setString(4, doctor.getHospitalAffiliation());
            ps.setString(5, doctor.getProfileImagePath());
            ps.setString(6, doctor.getAvailability());
            ps.setInt(7, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
