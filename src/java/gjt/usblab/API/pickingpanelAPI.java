package gjt.usblab.API;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import gjt.algorithm.*;
import gjt.usblab.Server.Server;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.Socket.baseSocket;
import gjt.usblab.bridge.QRCodeBridge;
import gjt.usblab.bridge.lendItemBridge;
import gjt.usblab.data.itemData;
import gjt.usblab.data.userData;
import gjt.usblab.nodeCluster.nodeCluster;

import javax.servlet.*;             // Tomcat 9
import javax.servlet.http.*;        // Tomcat 9


public class pickingpanelAPI  extends HttpServlet {
    public Integer lastQRCodeNo;
    public class retVal{
        public ArrayList<itemData> datas = new ArrayList<>();
        public itemData mainData;
        public String QRCodeURL;
        public long minutes;
        public long seconds;
        public String LEDcolor;
        public boolean finish;
        public boolean wait;
        public boolean end;
        public boolean pop;
        public itemData popData;

        public List<Integer> paths = new ArrayList<>();
        public retVal(){
            finish = false;
            wait = false;
            end = false;
            pop = false;
            LEDcolor = "black";
        }
        public void setPaths(List<Integer> pat){
            this.paths = pat;
        }
        public void setDatas( ArrayList<itemData> data){
            this.datas = data;
        }
        public void setMainData(itemData mData){
            this.mainData=mData;
        }
        public void setQRCodeURL(String url){
            this.QRCodeURL = url;
        }
        public void setM(long minutes){
            this.minutes = minutes;
        }
        public void setS(long seconds){
            this.seconds = seconds;
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // every 1 sec may refresh
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String next = request.getParameter("next");
        String end = request.getParameter("end");
        retVal result = new retVal();
        HttpSession s = request.getSession();
        if (s.getAttribute("userData") != null && ((userData) s.getAttribute("userData")).inPreBorrow ){
            userData uData = (userData) s.getAttribute("userData");
            result.setQRCodeURL(uData.PreBorrowQRCodeURL);

            int eNo = uData.eNo;
            int QRCodeNo = uData.PreBorrowQRCode;

            TSPSolver solver = new TSPSolver();

            solver.solve(QRCodeNo);
            // wait from 門禁 to check (available)
            boolean stop = false;

            if (QRCodeBridge.codeAvailable(QRCodeNo)){
                lendItemBridge.setCode(eNo,QRCodeNo);
                // get elements from eNo and QRCodeNo
                ArrayList<Long> tret = QRCodeBridge.getDistance(QRCodeNo);
                long minutes = tret.get(0);
                long seconds = tret.get(1);
                result.setM(minutes);
                result.setS(seconds);
                ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                    "SELECT * from PreBorrow INNER JOIN RegisterItem on PreBorrow.RiNo = RegisterItem.RiNo WHERE eNo = '"+eNo+"' AND QRCodeNo = '"+QRCodeNo+"' ",
                    "RiNo",
                    "PbCount",
                    "filename",
                    "HoldCount",
                    "Name");

                // if next

                result.LEDcolor = solver.getColor(QRCodeNo);
                if (next.equalsIgnoreCase("true")){
                    solver.nextResult(QRCodeNo);
                }
                if (end.equalsIgnoreCase("true")){
                    stop = true;
                }
                if (lendItemBridge.check(QRCodeNo)){
                    itemData popItem = lendItemBridge.get(QRCodeNo);
                    result.pop = true;
                    result.popData = popItem;
                }

                //

                HashMap<Integer,itemData> datas = new HashMap<>();
                ArrayList<itemData> ls = new ArrayList<>();
                for (HashMap<String,Object> obj : ret){
                    int RiNo = (int)obj.get("RiNo");
                    int PbCount = (int) obj.get("PbCount");
                    int holdCount = (int) obj.get("HoldCount");
                    String filename = (String) obj.get("filename");
                    String name = (String) obj.get("Name");
                    itemData iData = new itemData(RiNo, name, filename);
                    iData.setCount(PbCount);
                    iData.setHoldCount(holdCount);
                    datas.put(RiNo, iData);
                    ls.add(iData);
                }
                result.setDatas(ls);
                if (solver.test(QRCodeNo)){
                    ItemPath path = solver.getCurrent(QRCodeNo);
                    int Main_RiNo = path.RiNo;
                    result.setPaths(path.paths);
                    result.setMainData(datas.get(Main_RiNo));
                }else{
                    // end
                    stop = true;
                }

                if (solver.isLast(QRCodeNo)) {
                    result.finish = true;
                }

                if(lastQRCodeNo==null || !lastQRCodeNo.equals(Integer.valueOf(QRCodeNo))){
                    System.out.println("Send Socket Message from pickingpanelAPI.java : led on");
                    nodeCluster.getInstance().getNode(nodeCluster.instance.lastNodeID).dataChannel.ProcessSend("REDON-");
                    lastQRCodeNo = Integer.valueOf(QRCodeNo);
                }

            }else{  
                // disable 
                

                result.wait = true; // wait for qrcode
                // debug
                //QRCodeBridge.codeEnable(QRCodeNo);
            }


           result.end = stop;
        }
        // have to check  
        // 

        // return
        // 1. map info
        // 2. led color
        // 3. item list
        // 4. current item
        // 5. time left
        // 6. pop info (when lend)
        // 7. qrcode


        // after done.
        // recalc the left return to new db
        // 2/7 -> 0/5 
        //

        String jsonData = new Gson().toJson(result);
        response.getWriter().write(jsonData);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

