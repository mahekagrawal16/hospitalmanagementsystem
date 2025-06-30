<%-- 
    Document   : myappointment
    Created on : 28 Jun 2025, 10:59:47 pm
    Author     : mahekagrawal
--%>

<%@page import="java.util.List"%>
<%@page import="com.docvisitnow.model.Appointment"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>My Appointments - Patient Dashboard</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
                <a href="<%= request.getContextPath()%>/patient/dashboard">Dashboard</a>
                <hr>
                <a href="bookappointment.jsp">Book Appointment</a>
                <hr>
                <a href="<%= request.getContextPath()%>/patient/myappointment" class="active">My Appointments</a>
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
        <div class="main-content">
            <h2>My Appointments</h2>
            <p class="text-muted">Here are your upcoming and past appointments.</p>

            <div class="table-responsive mt-4">
                <table class="table table-striped table-hover">
                    <thead class="table-primary" style="background-color: #003366;">
                        <tr>
                            <th>Appointment ID</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Doctor</th>
                            <th>Hospital</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                            if (appointments != null && !appointments.isEmpty()) {
                                for (Appointment appt : appointments) {
                        %>
                        <tr>
                            <td><%= appt.getAppointmentId()%></td>
                            <td><%= appt.getAppointmentDate()%></td>
                            <td><%= appt.getAppointmentTime()%></td>
                            <td><%= appt.getDoctorName()%></td>
                            <td><%= appt.getHospitalName()%></td>
                            <td><span class="badge bg-info"><%= appt.getStatus()%></span></td>
                            <td>
                                <button class="btn btn-sm btn-warning"
                                        onclick="openRescheduleModal('<%= appt.getAppointmentId()%>', '<%= appt.getAppointmentDate()%>', '<%= appt.getAppointmentTime()%>')">
                                    Reschedule
                                </button>

                                <button class="btn btn-sm btn-danger"
                                        onclick="openCancelModal('<%= appt.getAppointmentId()%>')">
                                    Cancel
                                </button>
                            </td>

                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="7">No appointments found.</td>
                        </tr>
                        <%
                            }
                        %>

                    </tbody>
                </table>
            </div>
        </div>
        <!-- Reschedule Modal -->
        <div class="modal fade" id="rescheduleModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content border-0 shadow">
                    <div class="modal-header bg-warning text-dark">
                        <h5 class="modal-title">Reschedule Appointment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <form action="<%= request.getContextPath()%>/patient/myappointment" method="post">
                        <input type="hidden" name="action" value="reschedule">
                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">New Date</label>
                                <input type="date" class="form-control" id="rescheduleDate" name="newDate" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">New Time</label>
                                <input type="time" class="form-control" id="rescheduleTime" name="newTime" required>
                            </div>
                            <input type="hidden" id="rescheduleAppointmentId" name="appointmentId">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-warning">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- Cancel Modal -->
        <!-- Cancel Modal -->
        <div class="modal fade" id="cancelModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content border-0 shadow">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title">Cancel Appointment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <form action="<%= request.getContextPath()%>/patient/myappointment" method="post">
                        <input type="hidden" name="action" value="cancel">
                        <div class="modal-body">
                            <p>Are you sure?</p>
                            <div class="mb-3">
                                <label class="form-label">Reason</label>
                                <textarea class="form-control" id="cancelReason" name="reason" required></textarea>
                            </div>
                            <input type="hidden" id="cancelAppointmentId" name="appointmentId">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                            <button type="submit" class="btn btn-danger">Yes, Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- Script to trigger modals -->
        <script src="../js/patient-js/myappointment.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
