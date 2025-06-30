<%-- 
    Document   : profile-settings
    Created on : 29 Jun 2025, 10:22:17 pm
    Author     : mahekagrawal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Patient Profile & Settings</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/patient-css/common.css"/>
        <link rel="stylesheet" href="../css/patient-css/profile.css"/>
    </head>
    <body>
        <%
            com.docvisitnow.model.User user = (com.docvisitnow.model.User) session.getAttribute("user");
            String fullName = user != null ? user.getFullName() : "Patient";
        %>
        <div class="sidebar shadow">
            <h1 class="text-center mb-0 mt-3" style="font-size: 4rem;">👤</h1>
            <h4 class="text-center mt-2"><%= fullName%></h4>
            <div class="nav-group">
                <hr>
                <a href="<%= request.getContextPath()%>/patient/dashboard">Dashboard</a>
                <hr>
                <a href="bookappointment.jsp">Book Appointment</a>
                <hr>
                <a href="<%= request.getContextPath()%>/patient/myappointment">My Appointments</a>
                <hr>
                <a href="<%= request.getContextPath()%>/patient/medicalhistory">Medical History</a>
                <hr>
                <a href="<%= request.getContextPath()%>/patient/prescription">Prescriptions</a>
                <hr>
                <a href="emergency.jsp">Emergency Access</a>
                <hr>
                <a href="profile-settings.jsp" class="active">Profile & Settings</a>
                <hr>
            </div>
            <div class="nav-group">
                <a href="../homepage.html" onclick="confirmLogout()">Logout</a>
            </div>
        </div>

        <div class="container-main">
            <h2 class="mb-4">Your Profile</h2>
            <form id="profile-form" method="post" action="<%= request.getContextPath()%>/ProfileSettingsServlet">
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="full-name" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="full-name" name="fullName" value="<%= user.getFullName()%>" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail()%>" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone Number</label>
                            <input type="text" class="form-control" id="phone" name="phone" value="<%= user.getPhoneNumber()%>" required>
                        </div>
                        <div class="mb-3">
                            <%
                                String dobFormatted = "";
                                if (user.getDob() != null) {
                                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                    dobFormatted = sdf.format(user.getDob());
                                }
                            %>
                            <label for="dob" class="form-label">Date of Birth</label>
                            <input type="date" class="form-control" id="dob" name="dob" value="<%= dobFormatted%>" required>
                        </div>
                        <div class="mb-3">
                            <label for="gender" class="form-label">Gender</label>
                            <select class="form-select" id="gender" name="gender" required>
                                <option value="Male" <%= "Male".equals(user.getGender()) ? "selected" : ""%>>Male</option>
                                <option value="Female" <%= "Female".equals(user.getGender()) ? "selected" : ""%>>Female</option>
                                <option value="Other" <%= "Other".equals(user.getGender()) ? "selected" : ""%>>Other</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <textarea class="form-control" id="address" name="address" rows="3" required><%= user.getAddress() == null ? "" : user.getAddress().trim()%></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="blood-type" class="form-label">Blood Type</label>
                            <select class="form-select" id="blood-type" name="bloodType" required>
                                <option value="A+" <%= user.getBloodType().equals("A+") ? "selected" : ""%>>A+</option>
                                <option value="B+" <%= user.getBloodType().equals("B+") ? "selected" : ""%>>B+</option>
                                <option value="O+" <%= user.getBloodType().equals("O+") ? "selected" : ""%>>O+</option>
                                <option value="AB+" <%= user.getBloodType().equals("AB+") ? "selected" : ""%>>AB+</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="emergency-contact" class="form-label">Emergency Contact</label>
                            <input type="text" class="form-control" id="emergency-contact" name="emergencyContact" value="<%= user.getEmergencyContactName()%>" required>
                        </div>
                        <div class="mb-3">
                            <label for="insurance" class="form-label">Insurance Provider</label>
                            <input type="text" class="form-control" id="insurance" name="insurance" value="<%= user.getInsuranceProvider() != null ? user.getInsuranceProvider() : ""%>" required>
                        </div>
                    </div>
                </div>
                <h2 class="mt-5 mb-3">Account Settings</h2>
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="password" class="form-label">New Password</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter new password">
                        </div>
                        <div class="mb-3">
                            <label for="confirm-password" class="form-label">Confirm New Password</label>
                            <input type="password" class="form-control" id="confirm-password" name="confirmPassword" placeholder="Confirm new password">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="notifications" class="form-label">Email Notifications</label>
                            <select class="form-select" id="notifications" name="notifications">
                                <option value="enabled" <%= user.getEmailNotifications() ? "selected" : ""%>>Enabled</option>
                                <option value="disabled" <%= !user.getEmailNotifications() ? "selected" : ""%>>Disabled</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="privacy" class="form-label">Privacy Settings</label>
                            <select class="form-select" id="privacy" name="privacy">
                                <option value="public">Public</option>
                                <option value="private">Private</option>
                                <option value="custom">Custom</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="two-factor" class="form-label">Two-Factor Authentication</label>
                            <select class="form-select" id="two-factor" name="twoFactor">
                                <option value="enabled" <%= user.isTwoFactorAuth() ? "selected" : ""%>>Enabled</option>
                                <option value="disabled" <%= !user.isTwoFactorAuth() ? "selected" : ""%>>Disabled</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row mt-4">
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </div>
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-success">Save Settings</button>
                    </div>
                </div>
            </form>
        </div>

        <script src="../js/patient-js/profile.js"></script>

    </body>
</html>
