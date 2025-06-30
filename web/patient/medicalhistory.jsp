<%-- 
    Document   : medicalhistory
    Created on : 29 Jun 2025, 1:03:47 pm
    Author     : mahekagrawal
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>Medical History - Patient Dashboard</title>
        <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/patient-css/common.css"/>
        <link rel="stylesheet" href="../css/patient-css/medical.css"/>
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
                <a href="<%= request.getContextPath()%>/patient/medicalhistory" class="active">Medical History</a>
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

        <div class="main-content">
            <h2>Medical History</h2>
            <p class="text-muted">Here is a summary of your medical history.</p>

            <div class="card">
                <div class="card-header"><h5>Medical Conditions</h5></div>
                <div class="card-body">
                    <ul id="conditionList">
                        <%
                            List<String> conditions = (List<String>) request.getAttribute("conditions");
                            if (conditions != null) {
                                for (String condition : conditions) {
                        %>
                        <li class="d-flex align-items-center justify-content-between">
                            <span><%= condition%></span>
                            <div>
                                <!-- Edit Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="condition">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="value" value="<%= condition%>||<%= condition%>"> <!-- Replace second part with JS prompt -->
                                    <button type="button" class="btn btn-sm btn-edit" onclick="handleEdit(this)">Edit</button>
                                </form>

                                <!-- Delete Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="condition">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="value" value="<%= condition%>">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                        </li>

                        <% }
                            }%>
                    </ul>
                    <div class="form-container">
                        <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory"
                              class="d-flex align-items-center gap-2 mt-2">
                            <input type="hidden" name="type" value="condition">
                            <input type="text" name="value" class="form-control" placeholder="Enter new condition" required>
                            <button type="submit" name="action" value="add" class="btn btn-primary">Add</button>
                        </form>

                    </div>
                </div>
            </div>

            <!-- Surgeries -->
            <div class="card mt-3">
                <div class="card-header"><h5>Past Surgeries</h5></div>
                <div class="card-body">
                    <ul id="surgeryList">
                        <%
                            List<String> surgeries = (List<String>) request.getAttribute("surgeries");
                            if (surgeries != null) {
                                for (String surgery : surgeries) {%>
                        <li class="d-flex align-items-center justify-content-between">
                            <span><%= surgery%></span>
                            <div>
                                <!-- Edit Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="surgery">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="value" value="<%= surgery%>||<%= surgery%>"> <!-- Replace second part with JS prompt -->
                                    <button type="button" class="btn btn-sm btn-edit" onclick="handleEdit(this)">Edit</button>
                                </form>

                                <!-- Delete Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="surgery">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="value" value="<%= surgery%>">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                        </li>

                        <% }
                            }%>
                    </ul>
                    <div class="form-container">
                        <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory"
                              class="d-flex align-items-center gap-2 mt-2">
                            <input type="hidden" name="type" value="surgery">
                            <input type="text" name="value" class="form-control" placeholder="Enter new surgery" required>
                            <button type="submit" name="action" value="add" class="btn btn-primary">Add</button>
                        </form>

                    </div>
                </div>
            </div>

            <!-- Allergies -->
            <div class="card mt-3">
                <div class="card-header"><h5>Allergies</h5></div>
                <div class="card-body">
                    <ul id="allergyList">
                        <%
                            List<String> allergies = (List<String>) request.getAttribute("allergies");
                            if (allergies != null) {

                                for (String allergy : allergies) {%>
                        <li class="d-flex align-items-center justify-content-between">
                            <span><%= allergy%></span>
                            <div>
                                <!-- Edit Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="allergy">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="value" value="<%= allergy%>||<%= allergy%>"> <!-- Replace second part with JS prompt -->
                                    <button type="button" class="btn btn-sm btn-edit" onclick="handleEdit(this)">Edit</button>
                                </form>

                                <!-- Delete Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="allergy">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="value" value="<%= allergy%>">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                        </li>

                        <% }
                            }%>
                    </ul>
                    <div class="form-container">
                        <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory"
                              class="d-flex align-items-center gap-2 mt-2">
                            <input type="hidden" name="type" value="allergy">
                            <input type="text" name="value" class="form-control" placeholder="Enter new allergy" required>
                            <button type="submit" name="action" value="add" class="btn btn-primary">Add</button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Medications -->
            <div class="card mt-3">
                <div class="card-header"><h5>Current Medications</h5></div>
                <div class="card-body">
                    <ul id="medicationList">
                        <%
                            List<String> medications = (List<String>) request.getAttribute("medications");
                            if (medications != null) {

                                for (String medication : medications) {%>
                        <li class="d-flex align-items-center justify-content-between">
                            <span><%= medication%></span>
                            <div>
                                <!-- Edit Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="medication">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="value" value="<%= medication%>||<%= medication%>"> <!-- Replace second part with JS prompt -->
                                    <button type="button" class="btn btn-sm btn-edit" onclick="handleEdit(this)">Edit</button>
                                </form>

                                <!-- Delete Form -->
                                <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory" class="d-inline">
                                    <input type="hidden" name="type" value="medication">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="value" value="<%= medication%>">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                        </li>

                        <% }
                            }%>
                    </ul>
                    <div class="form-container">
                        <form method="post" action="<%= request.getContextPath()%>/patient/medicalhistory"
                              class="d-flex align-items-center gap-2 mt-2">
                            <input type="hidden" name="type" value="medication">
                            <input type="text" name="value" class="form-control" placeholder="Enter new medication" required>
                            <button type="submit" name="action" value="add" class="btn btn-primary">Add</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="../js/patient-js/medical.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
