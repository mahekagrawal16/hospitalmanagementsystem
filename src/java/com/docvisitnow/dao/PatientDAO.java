// File: PatientDAO.java
package com.docvisitnow.dao;

import com.docvisitnow.model.PatientDetails;
import com.docvisitnow.model.Appointment;
import com.docvisitnow.model.Prescription;
import com.docvisitnow.util.DBConnection;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDAO {

    public void insertPatientDetails(int userId, PatientDetails patient) {
        String sql = "UPDATE users SET emergency_contact_name = ?, emergency_contact_phone = ?, "
                + "blood_type = ?, insurance_provider = ?, allergies = ?, medical_history = ? "
                + "WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getEmergencyContactName());
            ps.setString(2, patient.getEmergencyContactPhone());
            ps.setString(3, patient.getBloodType());
            ps.setString(4, patient.getInsuranceProvider());
            ps.setString(5, patient.getAllergies());
            ps.setString(6, patient.getMedicalHistory());
            ps.setInt(7, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Total Appointments
    public static int getTotalAppointments(int patientId) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 2. Upcoming Appointments
    public static int getUpcomingAppointments(int patientId) {
        String sql = "SELECT COUNT(*) FROM appointments "
                + "WHERE patient_id = ? AND appointment_date >= CURDATE() AND status = 'Upcoming'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 3. Pending Prescriptions (from medical_records)
    public static int getPendingPrescriptions(int patientId) {
        String sql = "SELECT COUNT(*) FROM medical_records "
                + "WHERE patient_id = ? AND type = 'Prescription' AND description IS NULL";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 4. Emergency Access Enabled
    public static boolean isEmergencyAccessEnabled(int patientId) {
        String sql = "SELECT is_emergency_access_enabled FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_emergency_access_enabled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Recent Hospitals (Last 3 visited)
    public static List<String> getRecentHospitals(int patientId) {
        List<String> hospitals = new ArrayList<>();
        String sql = "SELECT h.name FROM appointments a JOIN hospitals h ON a.hospital_id = h.hospital_id WHERE a.patient_id = ? ORDER BY a.appointment_date DESC LIMIT 3";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hospitals.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitals; // ✅ Always returns at least an empty list
    }

    // 6. Recent Medical History (from medical_records)
    public static List<String> getRecentMedicalHistory(int patientId) {
        List<String> recent = new ArrayList<>();
        String sql = "SELECT type, title, description, medicine, last_modified FROM medical_records WHERE patient_id = ? ORDER BY last_modified DESC LIMIT 5";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                String title = rs.getString("title");
                String medicine = rs.getString("medicine");
                String desc = rs.getString("description");

                String record;
                if ("medication".equalsIgnoreCase(type) && medicine != null && !medicine.isEmpty()) {
                    record = "Medication: " + medicine + (desc != null && !desc.isEmpty() ? " (" + desc + ")" : "");
                } else if (title != null && !title.isEmpty()) {
                    record = capitalize(type) + ": " + title;
                } else {
                    continue;
                }

                recent.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recent;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // 7. Last 3 Appointments
    public static List<Appointment> getLastThreeAppointments(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.appointment_date, a.status, u.full_name AS doctor_name "
                + "FROM appointments a "
                + "JOIN users u ON a.doctor_id = u.user_id "
                + "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC LIMIT 3";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setDoctorName(rs.getString("doctor_name"));
                appt.setDate(rs.getDate("appointment_date"));
                appt.setStatus(rs.getString("status"));
                appointments.add(appt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // 8. Next Appointment
    public static Appointment getNextAppointment(int patientId) {
        String sql = "SELECT a.appointment_date, a.appointment_time, u.full_name AS doctor_name, d.specialization AS department "
                + "FROM appointments a "
                + "JOIN users u ON a.doctor_id = u.user_id "
                + "JOIN users d ON a.doctor_id = d.user_id "
                + "WHERE a.patient_id = ? AND a.appointment_date >= CURDATE() AND a.status = 'Upcoming' "
                + "ORDER BY a.appointment_date ASC, a.appointment_time ASC LIMIT 1";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Appointment next = new Appointment();
                next.setDoctorName(rs.getString("doctor_name"));
                next.setDepartment(rs.getString("department"));
                next.setAppointmentDate(rs.getDate("appointment_date"));
                next.setAppointmentTime(rs.getTime("appointment_time"));
                return next;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void rescheduleAppointment(int id, String date, String time) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE appointments SET appointment_date = ?, appointment_time = ?, status = 'Rescheduled' WHERE appointment_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, date);
            ps.setString(2, time);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelAppointment(int id, String reason) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE appointments SET status = 'Cancelled',reason = ? WHERE appointment_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reason);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<String>> getMedicalHistory(int patientId) {
        Map<String, List<String>> history = new HashMap<>();
        history.put("condition", new ArrayList<>());
        history.put("surgery", new ArrayList<>());
        history.put("allergy", new ArrayList<>());
        history.put("medication", new ArrayList<>());

        String sql = "SELECT type, title, description, medicine FROM medical_records WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String medicine = rs.getString("medicine");

                switch (type.toLowerCase()) {
                    case "condition":
                        if (title != null) {
                            history.get("condition").add(title);
                        }
                        break;
                    case "surgery":
                        if (title != null) {
                            history.get("surgery").add(title);
                        }
                        break;
                    case "allergy":
                        if (title != null) {
                            history.get("allergy").add(title);
                        }
                        break;
                    case "medication":
                        if (title != null) {
                            history.get("medication").add(title);
                        }
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return history;
    }

    public static void insertMedicalHistory(int patientId, String type, String value) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO medical_records (patient_id, type, title) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, patientId);
                ps.setString(2, type.toLowerCase()); // e.g., "condition", "surgery"
                ps.setString(3, value);              // e.g., "Diabetes"
                ps.executeUpdate();
            }
        }
    }

    public static void deleteMedicalHistory(int patientId, String type, String value) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM medical_records WHERE patient_id = ? AND type = ? AND title = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, patientId);
                ps.setString(2, type.toLowerCase());
                ps.setString(3, value);
                ps.executeUpdate();
            }
        }
    }

    public static void updateMedicalHistory(int patientId, String type, String oldValue, String newValue) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE medical_records SET title = ? WHERE patient_id = ? AND type = ? AND title = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newValue);
                ps.setInt(2, patientId);
                ps.setString(3, type.toLowerCase());
                ps.setString(4, oldValue);
                ps.executeUpdate();
            }
        }
    }

    public static List<Prescription> getPrescriptions(int patientId) {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT mr.medicine, mr.dosage, mr.description, mr.title, mr.visit_date, u.full_name AS doctor_name "
                + "FROM medical_records mr "
                + "JOIN users u ON mr.doctor_id = u.user_id "
                + "WHERE mr.patient_id = ? AND mr.type = 'Prescription' "
                + "ORDER BY mr.visit_date DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                Prescription p = new Prescription();
                p.setMedicine(rs.getString("medicine"));
                p.setDosage(rs.getString("dosage"));
                p.setInstructions(rs.getString("description"));
                p.setDiagnosis(rs.getString("title"));

                Date visitDate = rs.getDate("visit_date");
                p.setDate(visitDate != null ? new SimpleDateFormat("yyyy-MM-dd").format(visitDate) : "N/A");

                p.setDoctorName(rs.getString("doctor_name"));
                prescriptions.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptions;
    }
public static void updateEmergencyContact(int userId, String name, String phone) throws SQLException {
    String sql = "UPDATE users SET emergency_contact_name = ?, emergency_contact_phone = ? WHERE user_id = ?";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setInt(3, userId);
        ps.executeUpdate();
    }
}
public static List<String> getMedicalHistoryByType(int patientId, String type) {
    List<String> results = new ArrayList<>();
    String sql = "SELECT title FROM medical_records WHERE patient_id = ? AND type = ?";

    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, patientId);
        ps.setString(2, type.toLowerCase());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            results.add(rs.getString("title"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return results;
}


}
