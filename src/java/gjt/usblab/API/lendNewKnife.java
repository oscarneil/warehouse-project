package gjt.usblab.API;

import com.google.gson.Gson;
import gjt.usblab.Server.Server;
import gjt.usblab.bridge.stocktakingBridge;
import gjt.usblab.data.StockResultData;
import gjt.usblab.data.itemData;
import gjt.usblab.nodeCluster.nodeCluster;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import gjt.usblab.data.newKnifeDate;

import java.util.HashMap;

public class lendNewKnife extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            newKnifeDate borrowKnife = Server.getInstance().sqlConnection.searchNewKnifePreBorrow();
            if (borrowKnife != null) {
                int newKnifeNum = borrowKnife.getNewknifeNum();
                int lendCount = borrowKnife.getLendCount();
//                有掃碼器的話，下面這行註解掉!
//                Server.getInstance().sqlConnection.reduceNewKnifeCount(newKnifeNum, lendCount);
                String NewKnifeNumStr = newKnifeNum < 10 ? "0" + newKnifeNum : Integer.toString(newKnifeNum);
                String LendCountStr = lendCount < 10 ? "0" + lendCount : Integer.toString(lendCount);
                String returnPacketMessage = "01V$" + NewKnifeNumStr + LendCountStr + "-";
                Integer nodeID = Server.getInstance().sqlConnection.selectNodeIDVLimit1();
                if (nodeID != null) {
                    nodeCluster.getInstance().getNode(nodeID).dataChannel.ProcessSend(returnPacketMessage);
                }
                String jsonData = new Gson().toJson("Success lending new knife. KnifeNumber:" + NewKnifeNumStr + ",LendCount:" + LendCountStr);
                response.getWriter().write(jsonData);
            } else {
                String jsonData = new Gson().toJson("Fail to lending new knife.not find preborrow items.");
                response.getWriter().write(jsonData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
