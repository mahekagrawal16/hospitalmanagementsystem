// File: AdminDAO.java
package com.docvisitnow.dao;

import com.docvisitnow.model.AdminDetails;
import com.docvisitnow.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminDAO {
    public void insertAdminDetails(int userId, AdminDetails admin) {
        String sql = "UPDATE users SET admin_code = ?, role_description = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getAdminCode());
            ps.setString(2, admin.getRoleDescription());
            ps.setInt(3, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
