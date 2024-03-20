package gjt.usblab.Servlet;


import java.io.*;
import java.util.List;

import gjt.usblab.utils.logger;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9
 
// @WebServlet("/status")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class logs extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
      
      
      // Set the response MIME type of the response message
      response.setContentType("text/html");
      // Allocate a output writer to write the response message into the network socket
      String result = request.getParameter("nodeid");// logs?nodeid=...
      
      
      
      
      PrintWriter out = response.getWriter();
 
      // Write the response message, in an HTML page
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>logging</title></head>");
      out.println("<body>");
      
      // Generate a random number upon each request
      List<String> ret;
      if (result != null){
         int id = Integer.valueOf(result); 
         ret = logger.getInstance().getNodeLogs(id);
      }else{
         ret = logger.getInstance().getLogs();
      }
       
      for (String s:ret){
        out.println("<p>" + s + "</p>");
      }


      out.println("</body></html>");
      out.close();  // Always close the output writer
   }
}
