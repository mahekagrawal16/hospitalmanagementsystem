<%-- 
    Document   : emergency
    Created on : 29 Jun 2025, 5:45:32 pm
    Author     : mahekagrawal
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.docvisitnow.dao.PatientDAO"%>
<%@page import="com.docvisitnow.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Emergency Access</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="../css/patient-css/emergency.css"/>
        <link rel="stylesheet" href="../css/patient-css/common.css"/>
    </head>
    <body style="background-color: #f8f9fa;">
        <%
            com.docvisitnow.model.User user = (com.docvisitnow.model.User) session.getAttribute("user");
            String fullName = user != null ? user.getFullName() : "Patient";
        %>
        <!-- Sidebar -->
        <div class="sidebar shadow">
            <h1 class="text-center mb-0 mt-3" style="font-size: 4rem;">👤</h1>
            <h4 class="text-center mt-2"> <%= fullName%> </h4>
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
                <a href="emergency.jsp" class="active">Emergency Access</a>
                <hr>
                <a href="profile-settings.jsp">Profile & Settings</a>
                <hr>
            </div>
            <div class="nav-group">
                <a href="../homepage.html" onclick="confirmLogout()">Logout</a>
            </div>
        </div>

        <!-- Main Content -->
        <div class="container-fluid">
            <h2>Emergency Access</h2>
            <!-- Emergency Contact Section -->
            <div class="emergency-section">
                <h4>Emergency Contact</h4>
                <form method="post" id="emergency-contact-form" action="<%= request.getContextPath()%>/EmergencyContactServlet">
                    <div class="mb-3">
                        <label for="emergency-name" class="form-label">Contact Name</label>
                        <input type="text" class="form-control" id="emergency-name" name="emergencyName"
                               value="<%= user != null ? user.getEmergencyContactName() : ""%>" required>                    </div>
                    <div class="mb-3">
                        <label for="emergency-phone" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="emergency-phone" name="emergencyPhone"
                               value="<%= user != null ? user.getEmergencyContactPhone() : ""%>" required>                    </div>
                    <button type="submit" class="btn btn-primary">Update Contact</button>
                </form>

            </div>

            <!-- Medical Information Section -->
            <%
                String bloodType = user != null ? user.getBloodType() : "";
                List<String> allergies = user != null ? PatientDAO.getMedicalHistoryByType(user.getUserId(), "allergy") : new ArrayList<>();
                List<String> conditions = user != null ? PatientDAO.getMedicalHistoryByType(user.getUserId(), "condition") : new ArrayList<>();
            %>

            <div class="emergency-section">
                <h4>Emergency Medical Information</h4>
                <table class="table table-bordered">
                    <tbody>
                        <tr><th>Blood Type</th><td id="blood-type"><%= bloodType%></td></tr>
                        <tr><th>Allergies</th><td id="allergies"><%= String.join(", ", allergies)%></td></tr>
                        <tr><th>Medical History</th><td id="medical-history"><%= String.join(", ", conditions)%></td></tr>
                    </tbody>
                </table>
            </div>


            <%String imagePath = user != null ? user.getProfileImagePath() : null; %>

            <!-- Biometric Scanner Section -->
            <!-- Biometric Scanner Section -->
<div class="emergency-section">
    <h4>Biometric Scanner</h4>
    <p>Upload biometric image for quick identification.</p>

    <form action="<%= request.getContextPath() %>/UploadBiometricServlet" method="post" enctype="multipart/form-data">
        <% if (imagePath != null && !imagePath.isEmpty()) { %>
            <div class="mb-3">
                <label class="form-label">Previously Uploaded File:</label>
                <a href="<%= request.getContextPath() + "/" + imagePath %>" target="_blank" class="btn btn-outline-secondary">
                    <i class="bi bi-file-earmark-image"></i>
                    <%= imagePath.substring(imagePath.lastIndexOf("/") + 1) %>
                </a>
            </div>
        <% } %>

        <div class="mb-3">
            <label for="biometricImage" class="form-label">Choose Image</label>
            <input type="file" name="biometricImage" class="form-control" id="biometricImage" accept="image/*" required>
        </div>

        <button type="submit" class="btn btn-success">Upload Scan</button>
    </form>
</div>



            <script src="../js/patient-js/emergency.js"></script>
    </body>
</html>
