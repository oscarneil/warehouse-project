package gjt.usblab.Servlet;

import java.io.*;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;

import javax.servlet.annotation.*;  // Tomcat 9

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate the session to log the user out
        request.getSession().invalidate();
        response.sendRedirect("index.jsp"); // Redirect to the login page
    }

}