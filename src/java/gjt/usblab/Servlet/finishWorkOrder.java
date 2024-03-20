package gjt.usblab.Servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;
import gjt.usblab.data.itemData;
import gjt.usblab.data.userData;

import javax.servlet.annotation.*;  // Tomcat 9

public class finishWorkOrder extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Prepare data

        String stype = request.getParameter("type");
        int id = Integer.parseInt((String)request.getParameter("id"));


        // clear qr code in uData
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null){
            userData uData = (userData) s.getAttribute("userData");
            uData.clearPreBorrowCode();
            
        }
        response.sendRedirect("index.jsp");
    }

}
