package gjt.usblab.Servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9

public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get values from the form
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        // Process the received data (for demonstration, we're printing it)
        System.out.println("Received Account: " + account);
        System.out.println("Received Password: " + password);

        // You can perform further actions like saving the data to a database here

        // Send a response back to the client (for demonstration)
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Registration Successful</h2>");
        out.println("<p>Account: " + account + "</p>");
        out.println("<p>Password: " + password + "</p>");
        out.println("</body></html>");
    }
}
