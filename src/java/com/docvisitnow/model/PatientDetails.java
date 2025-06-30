/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docvisitnow.model;

/**
 *
 * @author mahekagrawal
 */
public class PatientDetails {
    private int userId;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String bloodType;
    private String insuranceProvider;
    private String medicalHistory;
    private String allergies;
    private boolean isEmergencyAccessEnabled;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public boolean isIsEmergencyAccessEnabled() {
        return isEmergencyAccessEnabled;
    }

    public void setIsEmergencyAccessEnabled(boolean isEmergencyAccessEnabled) {
        this.isEmergencyAccessEnabled = isEmergencyAccessEnabled;
    }
    
}
