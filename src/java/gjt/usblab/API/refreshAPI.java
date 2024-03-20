package gjt.usblab.API;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9
import com.google.gson.*;
import com.google.gson.Gson;

import gjt.usblab.utils.logger;

public class refreshAPI extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        //HashMap<String,Object> mp = new HashMap<String,Object>();


        String lS = request.getParameter("lastINT");
        // [{"text":"...","id":...},]
        int last = 0;
        try{
            last = Integer.parseInt(lS);
        }catch(Exception e){
            last = 0;
        }
        last++;


        

        String jsonData = new Gson().toJson( logger.getInstance().fetchlog(last));
        response.getWriter().write(jsonData);
    }
}
