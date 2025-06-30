<%-- 
    Document   : dashboard
    Created on : 28 Jun 2025, 7:48:13 pm
    Author     : mahekagrawal
--%>

<%@page import="com.docvisitnow.model.Appointment"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>Patient Dashboard - DocVisitNow</title>
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
                <h4 class="text-center mt-2"> <%= fullName%></h4>

                <div class="nav-group">
                    <hr>
                    <a href="<%= request.getContextPath() %>/patient/dashboard" class="active">Dashboard</a>
                    <hr>
                    <a href="bookappointment.jsp">Book Appointment</a>
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

            <!-- Main content -->
            <div class="main-content">
                <h2>Welcome, <%= fullName%> 👋</h2>
                <p>Quick overview of your healthcare stats and activity.</p>

                <div class="row">
                    <div class="col-md-3 mb-4">
                        <div class="card bg-primary text-white shadow p-3">
                            <h5>Total Appointments</h5>
                            <h3><%= request.getAttribute("totalAppointments")%></h3>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card bg-success text-white shadow p-3">
                            <h5>Upcoming</h5>
                            <h3><%= request.getAttribute("upcomingAppointments")%></h3>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card bg-warning text-dark shadow p-3">
                            <h5>Pending Prescriptions</h5>
                            <h3><%= request.getAttribute("pendingPrescriptions")%></h3>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card bg-info text-white shadow p-3">
                            <h5>Emergency Access</h5>
                            <h3><%=request.getAttribute("emergencyStatus")%></h3>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <!-- Recent Hospitals Visited -->
                    <div class="col-md-6 mb-4">
                        <div class="card shadow p-3">
                            <h5>Recent Hospitals Visited</h5>
                            <ul class="list-group">
                                <%
                                    List<String> recentHospitals = (List<String>) request.getAttribute("recentHospitals");
                                    if (recentHospitals != null && !recentHospitals.isEmpty()) {
                                        for (String hospital : recentHospitals) {
                                %>
                                <li class="list-group-item"><%= hospital%></li>
                                    <%
                                        }
                                    } else {
                                    %>
                                <li class="list-group-item">No hospital visits found.</li>
                                    <%
                                        }
                                    %>

                            </ul>
                        </div>
                    </div>

                    <!-- Latest Medical History -->
                    <div class="col-md-6 mb-4">
                        <div class="card shadow p-3">
                            <h5>Latest Medical History</h5>
                            <ul class="list-group">
                                <%
                                    List<String> recentHistory = (List<String>) request.getAttribute("recentHistory");
                                    if (recentHistory != null && !recentHistory.isEmpty()) {
                                        for (String record : recentHistory) {
                                %>
                                <li class="list-group-item"><%= record%></li>
                                    <%
                                        }
                                    } else {
                                    %>
                                <li class="list-group-item">No medical history found.</li>
                                    <% } %>

                            </ul>
                        </div>
                    </div>

                    <!-- Last 3 Appointments -->
                    <div class="col-md-6 mb-4">
                        <div class="card shadow p-3">
                            <h5>Last 3 Appointments</h5>
                            <ul class="list-group">
                                <%
                                    List<Appointment> lastAppointments = (List<Appointment>) request.getAttribute("lastAppointments");
                                    if (lastAppointments != null && !lastAppointments.isEmpty()) {
                                        for (Appointment appt : lastAppointments) {
                                %>
                                <li class="list-group-item">
                                    Dr. <%= appt.getDoctorName()%> - 
                                    <%= new java.text.SimpleDateFormat("dd MMM yyyy").format(appt.getAppointmentDate())%> - 
                                    <%= appt.getStatus()%>
                                </li>
                                <%
                                    }
                                } else {
                                %>
                                <li class="list-group-item">No recent appointments found.</li>
                                    <% } %>

                            </ul>
                        </div>
                    </div>

                    <!-- Next Appointment -->
                    <div class="col-md-6 mb-4">
                        <div class="card shadow p-3">
                            <h5>Next Appointment</h5>
                            <%
                                Appointment next = (Appointment) request.getAttribute("nextAppointment");
                                if (next != null) {
                            %>
                            <p><strong>Doctor:</strong> Dr. <%= next.getDoctorName()%></p>
                            <p><strong>Date:</strong> <%= new java.text.SimpleDateFormat("dd MMM yyyy").format(next.getAppointmentDate())%></p>
                            <p><strong>Time:</strong> <%= new java.text.SimpleDateFormat("hh:mm a").format(next.getAppointmentTime())%></p>
                            <a href="<%= request.getContextPath() %>/patient/myappointment" class="btn btn-primary btn-view">View Details</a>
                            <% } else { %>
                            <p>No upcoming appointments.</p>
                            <% }%>

                        </div>
                    </div>

                </div>

            </div>

            <script src="../js/patient-js/dashboard.js"></script>
        </body>
    </html>
