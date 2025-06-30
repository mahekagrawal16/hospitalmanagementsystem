<%@ page import="java.util.*,java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.docvisitnow.dao.*" %>
<%@ page import="com.docvisitnow.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Emergency Face Scanner</title>
    <link rel="shortcut icon" href="../images/icon.png" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/patient-css/common.css">
    <style>
        body {
            background-color: #f2f4f7;
        }
        .scanner-wrapper {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 60px 20px;
        }
        .scanner-card {
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 16px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
        }
        .scanner-title {
            font-size: 26px;
            font-weight: bold;
            margin-bottom: 25px;
            color: #333;
        }
        .btn-danger {
            transition: background-color 0.3s ease;
        }
        .btn-danger:hover {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body>

<div class="scanner-wrapper">
    <div class="scanner-card">
        <div class="scanner-title text-center">🚨 Emergency Face Scan</div>

        <form method="post" action="<%=request.getContextPath()%>/EmergencyScannerServlet" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="faceImage" class="form-label">Upload Image for Identification</label>
                <input type="file" name="faceImage" id="faceImage" class="form-control" accept="image/*" required>
            </div>
            <div class="d-grid">
                <button type="submit" class="btn btn-danger">Upload Image</button>
            </div>
        </form>

        <%-- Display match results --%>
        <%
            String match = request.getParameter("match");
            if ("1".equals(match)) {
        %>
            <div class="alert alert-success mt-4">
                <h5>✅ Match Found</h5>
                <p><strong>Name:</strong> <%= request.getAttribute("fullName") %></p>
                <p><strong>Blood Type:</strong> <%= request.getAttribute("bloodType") %></p>
                <p><strong>Emergency Contact:</strong> <%= request.getAttribute("emergencyContact") %></p>
            </div>
        <% } else if ("0".equals(match)) { %>
            <div class="alert alert-danger mt-4">❌ No match found for the uploaded image.</div>
        <% } %>
    </div>
</div>

</body>
</html>
