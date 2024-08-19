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
import java.util.ArrayList;

public class updateReturnableStatus extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String statusParam = request.getParameter("status");
        boolean isChecked = Boolean.parseBoolean(statusParam);
        Server.getInstance().sqlConnection.updateReturnableStatus(isChecked);
        String jsonData = new Gson().toJson("success");
        response.getWriter().write(jsonData);
    }
}
