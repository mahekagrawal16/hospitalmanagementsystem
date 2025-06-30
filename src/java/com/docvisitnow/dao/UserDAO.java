package com.docvisitnow.dao;

import com.docvisitnow.model.User;
import com.docvisitnow.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public int insertUser(User user) {
        int userId = -1;
        String sql = "INSERT INTO users (full_name, email, phone_number, gender, dob, address, role, password_hash, status)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getGender());

            if (user.getDob() != null) {
                ps.setDate(5, user.getDob());
            } else {
                System.out.println("DOB is null.");
                return -1;
            }

            ps.setString(6, user.getAddress());
            ps.setString(7, user.getRole());
            ps.setString(8, user.getPasswordHash());
            ps.setString(9, "Active");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error inserting user:");
            e.printStackTrace();
        }

        return userId;
    }

    public User login(String identifier, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE (email = ? OR phone_number = ?) AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, identifier);
            ps.setString(2, identifier);
            ps.setString(3, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));  // ✅ Important
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setBloodType(rs.getString("blood_type")); // ✅ ADD THIS
                user.setEmergencyContactName(rs.getString("emergency_contact_name")); // ✅
                user.setEmergencyContactPhone(rs.getString("emergency_contact_phone")); // ✅
                user.setProfileImagePath(rs.getString("profile_image_path"));
                user.setInsuranceProvider(rs.getString("insurance_provider"));
                user.setEmergencyAccessEnabled(rs.getBoolean("is_emergency_access_enabled"));

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDoctorNameById(int doctorId) {
        String sql = "SELECT full_name FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("full_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Doctor";
    }

    public static User getEmergencyContactInfo(int userId) throws SQLException {
        String sql = "SELECT emergency_contact_name, emergency_contact_phone, blood_type FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setEmergencyContactName(rs.getString("emergency_contact_name"));
                user.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
                user.setBloodType(rs.getString("blood_type"));
                return user;
            }
        }
        return null;
    }

    public static void updateEmergencyContact(int userId, String name, String phone) throws SQLException {
        String sql = "UPDATE users SET emergency_contact_name = ?, emergency_contact_phone = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        }
    }

    public static void saveBiometricPath(int userId, String imagePath) throws SQLException {
        String sql = "UPDATE users SET profile_image_path = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imagePath);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public static User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setGender(rs.getString("gender"));
                user.setDob(rs.getDate("dob"));
                user.setAddress(rs.getString("address"));
                user.setBloodType(rs.getString("blood_type"));
                user.setRole(rs.getString("role"));
                user.setEmergencyContactName(rs.getString("emergency_contact_name"));
                user.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
                user.setInsuranceProvider(rs.getString("insurance_provider"));
                user.setEmergencyAccessEnabled(rs.getBoolean("is_emergency_access_enabled"));
                user.setProfileImagePath(rs.getString("profile_image_path"));
                return user;
            }
        }
        return null;
    }

    public static void updateUserProfile(int userId, String phone, String address, boolean emailNotifications) throws SQLException {
        String sql = "UPDATE users SET phone_number = ?, address = ?, email_notifications = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setBoolean(3, emailNotifications);
            ps.setInt(4, userId);

            ps.executeUpdate();
        }
    }
public static void updateUserDOB(int userId, Date dob) throws SQLException {
    String sql = "UPDATE users SET dob = ? WHERE user_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDate(1, dob);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }
}
public static void updatePassword(int userId, String hashedPassword) throws SQLException {
    String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, hashedPassword);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }
}
public List<User> getAllPatientsWithImages() throws SQLException {
    List<User> list = new ArrayList<>();

    String sql = "SELECT user_id, full_name, blood_type, address, emergency_contact_name, emergency_contact_phone, profile_image_path " +
                 "FROM users WHERE role = 'Patient' AND profile_image_path IS NOT NULL";

    Connection conn = DBConnection.getConnection(); // adjust as per your utility
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setFullName(rs.getString("full_name"));
        u.setBloodType(rs.getString("blood_type"));
        u.setAddress(rs.getString("address"));
        u.setEmergencyContactName(rs.getString("emergency_contact_name"));
        u.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
        u.setProfileImagePath(rs.getString("profile_image_path"));
        list.add(u);
    }

    conn.close();
    return list;
}

}
