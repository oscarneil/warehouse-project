package gjt.usblab.API;

import com.google.gson.Gson;
import gjt.usblab.Server.Server;
import gjt.usblab.data.inventoryData;
import gjt.usblab.data.loadLendingData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class getInventoryData extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        int screwsPacket = Server.getInstance().sqlConnection.stocktakingConsumable2(1);
        int knifeCount = Server.getInstance().sqlConnection.stocktakingExpensive("11263137").getStockCount();
        inventoryData inventoryData = new inventoryData(knifeCount, screwsPacket);
        String jsonData = new Gson().toJson(inventoryData);
        response.getWriter().write(jsonData);
    }

}
