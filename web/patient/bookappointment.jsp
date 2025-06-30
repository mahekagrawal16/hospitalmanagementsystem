<%-- 
    Document   : bookappointment
    Created on : 28 Jun 2025, 9:19:09 pm
    Author     : mahekagrawal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.docvisitnow.model.Hospital, com.docvisitnow.model.DoctorDetails" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book Appointment - Patient Dashboard</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="../css/patient-css/common.css"/>
    </head>
    <body>
        <%
            com.docvisitnow.model.User user = (com.docvisitnow.model.User) session.getAttribute("user");
            String fullName = user != null ? user.getFullName() : "Patient";
        %>

        <!-- Sidebar -->
        <div class="sidebar shadow">
            <h1 class="text-center mb-0 mt-3" style="font-size: 4rem;">👤</h1>
            <h4 class="text-center mt-2"><%= fullName%></h4>

            <div class="nav-group">
                <hr>
                <a href="<%= request.getContextPath() %>/patient/dashboard">Dashboard</a>
                <hr>
                <a href="bookappointment.jsp" class="active">Book Appointment</a>
                <hr>
                <a href="<%= request.getContextPath() %>/patient/myappointment">My Appointments</a>
                <hr>
                <a href="<%= request.getContextPath() %>/patient/medicalhistory">Medical History</a>
                <hr>
                <a href="<%= request.getContextPath() %>/patient/prescription">Prescriptions</a>
                <hr>
                <a href="emergency.jsp">Emergency Access</a>
                <hr>
                <a href="profile-settings.jsp">Profile & Settings</a>
                <hr>
            </div>

            <div class="nav-group">
                <a href="../homepage.html" onclick="confirmLogout()">Logout</a>
            </div>
        </div>

        <!-- Main Content -->
        <div class="main-content" style="background-color: #f0f4fb;">
            <h2>📅 Book an Appointment</h2>
            <p>Fill in the form below to schedule your medical appointment.</p>

            <div class="card p-4 shadow">
                <form id="appointmentForm" action="<%= request.getContextPath()%>/BookAppointmentServlet" method="POST">
                    <div class="mb-3">
                        <label for="hospital" class="form-label">Select Hospital</label>
                        <select id="hospital" name="hospitalId" class="form-select" required>
                            <option value="">Choose a hospital</option>
                            <option value="1">Springfield General Hospital</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="doctor" class="form-label">Select Doctor</label>
                        <select id="doctor" name="doctorId" class="form-select" required>
                            <option value="">Choose a doctor</option>
                            <option value="7">Dr. Rahul Tripathi (Dermatologist)</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="date" class="form-label">Appointment Date</label>
                        <input type="date" id="date" name="appointmentDate" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="time" class="form-label">Appointment Time</label>
                        <input type="time" id="time" name="appointmentTime" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="reason" class="form-label">Reason for Visit</label>
                        <textarea id="reason" name="reason" rows="3" class="form-control" placeholder="Describe your symptoms..." required></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Book Appointment</button>
                </form>


            </div>
        </div>
        <script src="../js/patient-js/bookappointment.js"></script>
    </body>
</html>
