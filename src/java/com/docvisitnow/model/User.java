/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docvisitnow.model;
import java.sql.Date;

/**
 *
 * @author mahekagrawal
 */
public class User {
    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String gender;
    private Date dob;
    private String bloodType;
    private String address;
    private String role;
    private String status;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String profileImagePath;
    private boolean emailNotifications;
    private boolean twoFactorAuth;
    private String insuranceProvider;
    private boolean emergencyAccessEnabled;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
public String getProfileImagePath() {
    return profileImagePath;
}

public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
}
public boolean getEmailNotifications() {
    return emailNotifications;
}

public void setEmailNotifications(boolean emailNotifications) {
    this.emailNotifications = emailNotifications;
}
public boolean isTwoFactorAuth() {
    return twoFactorAuth;
}

public void setTwoFactorAuth(boolean twoFactorAuth) {
    this.twoFactorAuth = twoFactorAuth;
}
public String getInsuranceProvider() {
    return insuranceProvider;
}

public void setInsuranceProvider(String insuranceProvider) {
    this.insuranceProvider = insuranceProvider;
}
public boolean isEmergencyAccessEnabled() {
    return emergencyAccessEnabled;
}

public void setEmergencyAccessEnabled(boolean emergencyAccessEnabled) {
    this.emergencyAccessEnabled = emergencyAccessEnabled;
}

}
