// File: HospitalDAO.java
package com.docvisitnow.dao;

import com.docvisitnow.model.Hospital;
import com.docvisitnow.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class HospitalDAO {
    public void insertHospitalDetails(int adminUserId, Hospital hospital) {
        String sql = "INSERT INTO hospitals (name, type, logo_path, address, city, contact_email, phone_number, affiliated_doctors, admin_user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hospital.getName());
            ps.setString(2, hospital.getType());
            ps.setString(3, hospital.getLogoPath());
            ps.setString(4, hospital.getAddress());
            ps.setString(5, hospital.getCity());
            ps.setString(6, hospital.getContactEmail());
            ps.setString(7, hospital.getPhoneNumber());
            ps.setString(8, hospital.getAffiliatedDoctors());
            ps.setInt(9, adminUserId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
