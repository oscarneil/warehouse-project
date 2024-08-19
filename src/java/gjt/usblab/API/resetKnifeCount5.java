package gjt.usblab.API;

import com.google.gson.Gson;
import gjt.usblab.Server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class resetKnifeCount5 extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Server.getInstance().sqlConnection.SQLSafeUpdates();
        Server.getInstance().sqlConnection.deleteLendPreBorrow();
        Server.getInstance().sqlConnection.deleteLendData();
        Server.getInstance().sqlConnection.resetKnifeCount5();
        String jsonData = new Gson().toJson("success");
        response.getWriter().write(jsonData);
    }
}
