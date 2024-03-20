package gjt.usblab.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import gjt.usblab.Server.Server;
import gjt.usblab.data.itemData;
import gjt.usblab.data.userData;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9
import javax.servlet.annotation.*;  // Tomcat 9
import com.google.gson.*;
public class listPreBorrowAPI  extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<itemData> datas = new ArrayList<>();
        
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null){
            userData uData = (userData) s.getAttribute("userData");

            int eNo = uData.eNo;
            // list all 註冊物品
            ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT * from PreBorrow INNER JOIN RegisterItem on PreBorrow.RiNo = RegisterItem.RiNo WHERE eNo = '"+eNo+"' AND QRCodeNo IS NULL ",
                "RiNo",
                "PbCount",
                "filename",
                "Name");

            int size = ret.size();
            //SELECT RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename from RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo GROUP BY RegisterItem.RiNo
            for (HashMap<String,Object> obj : ret){
                int RiNo = (int)obj.get("RiNo");
                int PbCount = (int) obj.get("PbCount");
                String filename = (String) obj.get("filename");
                String name = (String) obj.get("Name");
                itemData iData = new itemData(RiNo, name, filename);
                iData.setCount(PbCount);

                datas.add( iData);
            }
        }


        String jsonData = new Gson().toJson(datas);
        response.getWriter().write(jsonData);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
