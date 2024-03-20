package gjt.usblab.Servlet;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9

import gjt.usblab.Main.main;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Server.Server;
import gjt.usblab.data.itemData;
import gjt.usblab.data.userData;

import javax.servlet.annotation.*;  // Tomcat 9

public class itemInfo extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Prepare data

        String stype = request.getParameter("type");
        int id = Integer.parseInt((String)request.getParameter("id"));
        request.setAttribute("img_path", "NULL");
        request.setAttribute("name", "查無此資料");
        request.setAttribute("count", "-1");
        request.setAttribute("info", "");
        request.setAttribute("id", -1);

        
        // Set the attribute to be passed to the JSP
        if (stype.equalsIgnoreCase("storage")){
            ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename from RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo WHERE RegisterItem.RiNo="+id+" GROUP BY RegisterItem.RiNo",
                "RiNo",
                "Name",
                "filename");
            if (ret.size() > 0){
                HashMap<String,Object> mp = ret.get(0);
                
                int r_id = (int)mp.get("RiNo");
                String name = (String) mp.get("Name");
                String filename = (String) mp.get("filename");
                itemData iData = new itemData(r_id, name, filename);
            
                int ava_count = Server.getInstance().sqlConnection.getAvailableItem(r_id);


                request.setAttribute("img_path", iData.fullPath);
                request.setAttribute("name", name);
                request.setAttribute("count", ava_count);
                request.setAttribute("info", "");
                request.setAttribute("id", id);
            }
            
        }else{

            ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename,count from RegisterItem INNER JOIN Consumables on RegisterItem.RiNo = Consumables.RiNo WHERE RegisterItem.RiNo="+id+" GROUP BY RegisterItem.RiNo",
                "RiNo",
                "Name",
                "filename",
                "count");
            if (ret.size() > 0){
                HashMap<String,Object> mp = ret.get(0);
                
                int r_id = (int)mp.get("RiNo");
                String name = (String) mp.get("Name");
                String filename = (String) mp.get("filename");
                itemData iData = new itemData(r_id, name, filename);

                int ava_count = (int) mp.get("count");


                request.setAttribute("img_path", iData.fullPath);
                request.setAttribute("name", name);
                request.setAttribute("count", ava_count);
                request.setAttribute("info", "");
                request.setAttribute("id", id);
            }
        }
        



        // Use a dynamic resource value in getRequestDispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("iteminfo.jsp");
        dispatcher.forward(request, response);

    }

}