package gjt.usblab.API;

import com.google.gson.Gson;
import gjt.usblab.Server.Server;
import gjt.usblab.data.loadLendingData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loadSwitchState extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Boolean result = Server.getInstance().sqlConnection.searchReturnableStatus();
        String jsonData = new Gson().toJson(result);
        response.getWriter().write(jsonData);
    }
}
