package gjt.usblab.Servlet;
import java.io.*;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;
import gjt.usblab.data.userData;

import javax.servlet.annotation.*;  // Tomcat 9

public class lendServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // id 
        // amount
        // user id
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null){
            userData uData = (userData) s.getAttribute("userData");
            
            int id = Integer.parseInt(request.getParameter("id"));
            int count = Integer.parseInt(request.getParameter("cnt"));
            // focus on PreBorrow then check ()
            // 1. had RiNo with no QRCodeNo
            //   - 
            // 2. with QRCodeNo
            //   - 
            // 
            // check RiNo and QRCodeNo IS NOT NULL first
            //   exists - update value
            //   non    - insert value
            Server.getInstance().sqlConnection.lendItem(uData.eNo,id,count);
        }
        response.sendRedirect("preborrow.jsp");
    }

}