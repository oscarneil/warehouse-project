package gjt.usblab.API;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class pbUpdateAPI  extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArrayList<String> datas = new ArrayList<>();
        
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null){
            userData uData = (userData) s.getAttribute("userData");

            int eNo = uData.eNo;
            

            // Read the JSON string from the request
            StringBuilder jsonStr = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    jsonStr.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                // Use Gson to deserialize the JSON string into an array
                Gson gson = new Gson();
                int[] receivedArray = gson.fromJson(jsonStr.toString(), int[].class);

                // Now you have the array in receivedArray
                // You can process the array as needed
                for (int item : receivedArray) {

                }

                ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                    "SELECT * from PreBorrow INNER JOIN RegisterItem on PreBorrow.RiNo = RegisterItem.RiNo WHERE eNo = '"+eNo+"' AND QRCodeNo IS NULL ",
                    "RiNo",
                    "PbCount",
                    "filename",
                    "Name",
                    "PbNo");

                int size = ret.size();
                System.out.println(receivedArray.length + " -- " + size);
                int index = 0;
                //SELECT RegisterItem.RiNo,RegisterItem.Name,RegisterItem.filename from RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo GROUP BY RegisterItem.RiNo
                for (HashMap<String,Object> obj : ret){
                    int RiNo = (int)obj.get("RiNo");
                    int pbno = (int)obj.get("PbNo");
                    String filename = (String) obj.get("filename");
                    String name = (String) obj.get("Name");
                    
                    //update 
                    if (receivedArray[index] == 0){
                        Server.getInstance().sqlConnection.deletePBItem(eNo, RiNo, pbno);
                    }else{
                        Server.getInstance().sqlConnection.updatePBItem(eNo, RiNo,receivedArray[index] , pbno);
                    }

                    index++;
                }
                
            }catch(Exception e){

            }
        }
        datas.add("ok");
        String jsonData = new Gson().toJson(datas);
        response.getWriter().write(jsonData);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
