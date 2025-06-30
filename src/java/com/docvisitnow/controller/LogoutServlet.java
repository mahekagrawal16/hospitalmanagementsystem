// File: LogoutServlet.java
package com.docvisitnow.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Avoid creating if not exists
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("homepage.html");
    }
}
