package com.docvisitnow.controller;

import com.docvisitnow.dao.*;
import com.docvisitnow.model.*;
import com.docvisitnow.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String rawPassword = request.getParameter("password");

        String password;
        try {
            password = PasswordUtil.hashPassword(rawPassword);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, "Password hashing failed", ex);
            request.setAttribute("error", "Something went wrong while securing your password.");
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }

        String gender = request.getParameter("gender");
        String dobParam = request.getParameter("dob");
        java.sql.Date dob = null;

        try {
            if (dobParam == null || dobParam.isEmpty()) {
                request.setAttribute("error", "Date of Birth is required.");
                request.getRequestDispatcher("registrationerror.html").forward(request, response);
                return;
            }

            // Parse dd-MM-yyyy to java.util.Date
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date utilDate = formatter.parse(dobParam);

            // Convert to java.sql.Date
            dob = new java.sql.Date(utilDate.getTime());

        } catch (Exception e) {
            request.setAttribute("error", "Invalid date format. Please use dd-MM-yyyy.");
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || role == null || role.trim().isEmpty()
                || dobParam == null) {
            request.setAttribute("error", "Full Name, Email, Role, and DOB are required.");
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }
        if (dobParam == null || dobParam.trim().isEmpty()) {
            request.setAttribute("error", "Date of Birth is required.");
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPasswordHash(password);
        user.setGender(gender);
        user.setDob(dob);
        user.setAddress(address);
        user.setRole(role);

        int userId = new UserDAO().insertUser(user);

        if (userId <= 0) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.WARNING, "User registration failed for: {0}", email);
            request.setAttribute("error", "User registration failed.");
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }

        try {
            switch (role) {
                case "patient" -> {
                    PatientDetails patient = new PatientDetails();
                    patient.setEmergencyContactName(request.getParameter("emergencyContactName"));
                    patient.setEmergencyContactPhone(request.getParameter("emergencyContactPhone"));
                    patient.setBloodType(request.getParameter("bloodType"));
                    patient.setAllergies(request.getParameter("allergies"));
                    patient.setMedicalHistory(request.getParameter("medicalHistory"));
                    patient.setInsuranceProvider(request.getParameter("insuranceProvider"));
                    new PatientDAO().insertPatientDetails(userId, patient);
                }
                case "doctor" -> {
                    DoctorDetails doctor = new DoctorDetails();
                    doctor.setSpecialization(request.getParameter("specialization"));
                    doctor.setLicenseNumber(request.getParameter("licenseNumber"));

                    String yearsStr = request.getParameter("yearsExperience");
                    if (yearsStr == null || yearsStr.isEmpty()) {
                        request.setAttribute("error", "Years of experience is required for doctors.");
                        request.getRequestDispatcher("registrationerror.html").forward(request, response);
                        return;
                    }

                    doctor.setYearsExperience(Integer.parseInt(yearsStr));
                    doctor.setHospitalAffiliation(request.getParameter("hospitalAffiliation"));
                    doctor.setProfileImagePath("uploads/" + request.getParameter("doctorProfilePic"));
                    doctor.setAvailability(request.getParameter("availability"));

                    new DoctorDAO().insertDoctorDetails(userId, doctor);
                }
                case "admin" -> {
                    AdminDetails admin = new AdminDetails();
                    admin.setAdminCode(request.getParameter("adminCode"));
                    admin.setRoleDescription(request.getParameter("roleDescription"));
                    new AdminDAO().insertAdminDetails(userId, admin);
                }
                case "hospital" -> {
                    Hospital hospital = new Hospital();
                    hospital.setName(request.getParameter("hospitalName"));
                    hospital.setAddress(request.getParameter("hospitalAddress"));
                    hospital.setCity(request.getParameter("city"));
                    hospital.setType(request.getParameter("hospitalType"));
                    hospital.setLogoPath("uploads/" + request.getParameter("doctorProfilePic"));
                    hospital.setContactEmail(email);
                    hospital.setPhoneNumber(phone);
                    hospital.setAffiliatedDoctors(request.getParameter("affiliatedDoctors"));
                    new HospitalDAO().insertHospitalDetails(userId, hospital);
                }
                default -> {
                    request.setAttribute("error", "Invalid role provided.");
                    request.getRequestDispatcher("registrationerror.html").forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, "Role-specific registration failed", e);
            request.setAttribute("error", role.substring(0, 1).toUpperCase() + role.substring(1) + " registration failed: " + e.getMessage());
            request.getRequestDispatcher("registrationerror.html").forward(request, response);
            return;
        }

        Logger.getLogger(RegisterServlet.class.getName()).log(Level.INFO, "User registered successfully: {0} (Role: {1})", new Object[]{email, role});
        response.sendRedirect("homepage.html?success=1");
    }
}
