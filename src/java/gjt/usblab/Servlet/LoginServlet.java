package gjt.usblab.Servlet;

import java.io.*;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;
import gjt.usblab.data.userData;

import javax.servlet.annotation.*;  // Tomcat 9

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get values from the login form
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        // Perform authentication (for demonstration, we use a simple example)
        userData isAuthenticated = Server.getInstance().sqlConnection.authenticate(account, password);
        System.out.println(isAuthenticated);
        if (isAuthenticated != null) {
            // Successful login
            
            request.getSession().setAttribute("login", true);
            request.getSession().setAttribute("userData", isAuthenticated);
            response.sendRedirect("index.jsp");
        } else {
            // Failed login
            response.sendRedirect("login.jsp");
        }
    }

}