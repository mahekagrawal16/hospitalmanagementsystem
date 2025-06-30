<%-- 
    Document   : Prescription
    Created on : 29 Jun 2025, 4:34:52 pm
    Author     : mahekagrawal
--%>

<%@page import="com.docvisitnow.model.Prescription"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.*, com.docvisitnow.model.Prescription" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Prescriptions - Patient Dashboard</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/patient-css/common.css"/>
        <link rel="stylesheet" href="../css/patient-css/prescription.css"/>
    </head>
    <body>
        <%
            com.docvisitnow.model.User user = (com.docvisitnow.model.User) session.getAttribute("user");
            String fullName = user != null ? user.getFullName() : "Patient";
        %>
        <!-- Sidebar -->
        <div class="sidebar shadow">
            <h1 class="text-center mb-0 mt-3" style="font-size: 4rem;">👤</h1>
            <h4 class="text-center mt-2"><%=fullName%></h4>

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
                <a href="<%= request.getContextPath()%>/patient/prescription" class="active">Prescriptions</a>
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
            <h2>Prescriptions</h2>
            <p class="text-muted">Here you can view your prescriptions provided by your healthcare providers.</p>

            <!-- Prescription Table -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mt-2">Your Prescription History</h5>
                </div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Prescription Date</th>
                                <th scope="col">Doctor</th>
                                <th scope="col">Medication</th>
                                <th scope="col">Dosage</th>
                                <th scope="col">Instructions</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Prescription> prescriptions = (List<Prescription>) request.getAttribute("prescriptions");
                                if (prescriptions != null && !prescriptions.isEmpty()) {
                                    for (Prescription p : prescriptions) {
                            %>
                            <tr>
                                <td><%= p.getDate()%></td>
                                <td><%= p.getDoctorName()%></td>
                                <td><%= p.getMedicine()%></td>
                                <td><%= p.getDosage()%></td>
                                <td><%= p.getInstructions()%></td>
                                <td>
                                    <button class="btn-view" data-prescription='{
                                            "date": "<%= p.getDate()%>",
                                            "doctor": "<%= p.getDoctorName()%>",
                                            "medication": "<%= p.getMedicine()%>",
                                            "dosage": "<%= p.getDosage()%>",
                                            "instructions": "<%= p.getInstructions()%>",
                                            "diagnosis": "<%= p.getDiagnosis()%>"
                                            }'>View</button>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr><td colspan="6">No prescriptions found.</td></tr>
                            <%
                                }
                            %>



                        </tbody>

                    </table>
                </div>
            </div>

            <!-- Prescription Details Modal -->
            <div id="prescriptionModal" class="modal fade" tabindex="-1" aria-hidden="true" data-bs-backdrop="static" data-bs-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Prescription Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="modal-body">
                                <p><strong>Doctor:</strong> <span id="modal-doctor"></span></p>
                                <p><strong>Medication:</strong> <span id="modal-medication"></span></p>
                                <p><strong>Dosage:</strong> <span id="modal-dosage"></span></p>
                                <p><strong>Instructions:</strong> <span id="modal-instructions"></span></p>
                                <p><strong>Diagnosis:</strong> <span id="modal-diagnosis"></span></p>
                                <p><strong>Prescription Date:</strong> <span id="modal-date"></span></p>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn-update" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <script src="../js/patient-js/prescription.js"></script>

    </body>
</html>
