package gjt.usblab.API;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9

import com.google.gson.*;

import com.sun.mail.imap.Rights;
import gjt.usblab.bridge.lendItemBridge;
import gjt.usblab.nodeCluster.nodeCluster;
import gjt.usblab.utils.logger;
import gjt.usblab.Server.Server;

public class turnOffLED extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");

//        int nodeID = Server.getInstance().sqlConnection.selectNodeIDALimit1();
//        System.out.println("------------------nodeID:" + nodeID + "-------------");
//        nodeCluster.getInstance().getNode(nodeID).dataChannel.ProcessSend("01L$GRNOF-");
//        System.out.println("------------------01L$GRNOF-------------");

        String jsonData = new Gson().toJson("success");
        response.getWriter().write(jsonData);
    }
}
