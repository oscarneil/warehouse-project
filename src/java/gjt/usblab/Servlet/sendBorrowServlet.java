package gjt.usblab.Servlet;
import java.io.*;
import java.security.SecureRandom;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;
import gjt.usblab.data.userData;
import gjt.usblab.utils.QRCodeGenerator;

import javax.servlet.annotation.*;  // Tomcat 9

public class sendBorrowServlet extends HttpServlet {
    public static String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomText = new StringBuilder();

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomText.append(randomChar);
        }

        return randomText.toString();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // id 
        // amount
        // user id
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null){
            userData uData = (userData) s.getAttribute("userData");

            // create new QRCode that not exists
            String qrCode = generateRandomText(40);

            String path = QRCodeGenerator.make(qrCode);
            System.out.println(path);
            int qrCodeID = Server.getInstance().sqlConnection.addQRCode(qrCode);
            // update PreBorrow where eNo = eNo and QRCode = null
            System.out.println(qrCodeID);
            Server.getInstance().sqlConnection.updatePBItemWithQRCode(uData.eNo,qrCodeID);
            // redirect to  pickingpanel.jsp
            uData.setPreBorrowCode(qrCodeID);
            uData.setPreBorrowCodeURL(path);
            response.sendRedirect("pickingpanel.jsp");
        }   
        else response.sendRedirect("index.jsp");
    }

}