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

import gjt.usblab.utils.logger;

public class testAPI extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        TreeMap<String,String> mp = new TreeMap<String,String>();
        
        mp.put("status", "ok");
        System.out.println("visited by "+request.getContextPath());
        ArrayList<String > pp = new ArrayList<>();
        
        mp.put("id",request.getParameter("id"));

        String jsonData = new Gson().toJson(mp);
        logger.getInstance().log(jsonData);
        response.getWriter().write(jsonData);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
 }
 