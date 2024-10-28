package gjt.usblab.API;

import com.google.gson.Gson;
import gjt.usblab.Server.Server;
import gjt.usblab.data.itemData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class listNewElement extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<itemData> datas = new ArrayList<>();
        // list all 註冊物品
        ArrayList<HashMap<String, Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT shID,RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename from RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo INNER JOIN Shelf on RegisterItem.shNo = Shelf.shNo WHERE " +
                        "RegisterItem.Name LIKE 'knife%' GROUP BY RegisterItem.RiNo",
                "RiNo",
                "Name",
                "filename",
                "shID");
        //SELECT RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename from RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo GROUP BY RegisterItem.RiNo
        for (HashMap<String, Object> obj : ret) {
            int r_id = (int) obj.get("RiNo");
            String name = (String) obj.get("Name");
            String filename = (String) obj.get("filename");
            String shNo = (String) obj.get("shID");
            itemData iData = new itemData(r_id, name, filename);
            iData.setShelfNo(shNo);
            // item?type=storage&id=1
            iData.setURL("item?type=storage&id=" + r_id);
            int ava_count = Server.getInstance().sqlConnection.getAvailableItem(r_id);
            iData.setCount(ava_count);
            datas.add(iData);

        }

        String jsonData = new Gson().toJson(datas);
        response.getWriter().write(jsonData);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
