package gjt.usblab.nodeCluster.connectionChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gjt.usblab.Exception.InvalidPacketException;
import gjt.usblab.Server.Server;
import gjt.usblab.Socket.Packet.PacketValue;
import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.Socket.Packet.cmd;
import gjt.usblab.bridge.QRCodeBridge;
import gjt.usblab.bridge.lendItemBridge;
import gjt.usblab.bridge.stocktakingBridge;
import gjt.usblab.data.StockResultData;
import gjt.usblab.nodeCluster.node;
import gjt.usblab.nodeCluster.nodeCluster;
import gjt.usblab.nodeCluster.nodeType;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.utils.logger;

import javax.ejb.Singleton;

public abstract class BaseConnectionChannel {

    protected node instance;
    protected RecvThread recvThread;
    protected SendThread sendThread;
    protected baseSocket connectionSocket;
    protected boolean terminated = false;
    private static final AtomicInteger nodeDeviceIndex = new AtomicInteger(0);
    private CountDownLatch latch;

    public BaseConnectionChannel(baseSocket cSocket, node instance, CountDownLatch latch) {
        try {
            this.instance = instance;
            this.connectionSocket = cSocket;
            this.latch = latch;
            recvThread = new RecvThread(connectionSocket, this, latch);
            sendThread = new SendThread(connectionSocket, this);
            Thread recv = new Thread(recvThread);
            Thread send = new Thread(sendThread);
            //logger.getInstance().log(connectionSocket.toString());
            recv.start();
            send.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // if disconnect
    // to be synchronized if there have alot of thread to disconnect
    public abstract void disconnect();

    /**
     * call this when recv a packet from the socket
     *
     * @param p the packet
     */

    public abstract void ProcessRecv(BasePacket p);

    public void Recv(String receivePacket) throws InvalidPacketException {
        PacketValue packetValueInstance = PacketValue.getInstance();
        packetValueInstance.setReceivePacket(receivePacket);
        // 封包類型:03
        if (packetValueInstance.getPacketCategory() == 3) {
            switch (packetValueInstance.getPacketType()) {
                case "A":
                    // 03A:qrcode (門禁: Access Qrcode)
                    if (!packetValueInstance.getPacketMessage().get(0).isBlank()) {
                        String packetMessageString = packetValueInstance.getPacketMessage().get(0);
                        System.out.println("收到交易封包(Access)，設備類型:" + packetValueInstance.getPacketType() + "，封包內容:" + packetMessageString);
                        System.out.println(this.instance + " enable QR CODE: " + packetMessageString);
                        ArrayList<HashMap<String, Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                                "SELECT * from QRCode WHERE Info = '" + packetMessageString + "'",
                                "QRCodeNo");
                        if (ret.isEmpty()) {
                            System.out.println("code not found!");
                            return;
                        }
                        int QRCodeNo = (int) ret.get(0).get("QRCodeNo");
                        QRCodeBridge.codeEnable(QRCodeNo);

                        if (!Server.getInstance().sqlConnection.isNewKnife(QRCodeNo)) {
                            int nodeID = Server.getInstance().sqlConnection.selectNodeIDALimit1();
                            System.out.println("------------------nodeID:" + nodeID + "-------------");
                            nodeCluster.getInstance().getNode(nodeID).dataChannel.ProcessSend("01L$GRNON-");
                            System.out.println("------------------01L$GRNON-------------");
                        }
                    }
                    break;

                //  03C:cabinetID:weight:RFID (耗材: three packet messages)
                case "C":
                    System.out.println("收到交易封包(Access)，設備類型:" + packetValueInstance.getPacketType() + "，封包內容:" + packetValueInstance.getPacketMessage());
                    if (packetValueInstance.getPacketMessage().size() == 3) {
                        int ConsumableCabinetID = Integer.parseInt(packetValueInstance.getPacketMessage().get(0), 2);
                        float ConsumableWeight = Float.parseFloat(packetValueInstance.getPacketMessage().get(1));
                        ConsumableWeight *= 1000;
                        String UserRFID = packetValueInstance.getPacketMessage().get(2);
                        int PrePacketCount = Server.getInstance().sqlConnection.searchConsumerPreCount(ConsumableCabinetID);
                        int NowPacketCount = (int) Math.floor(ConsumableWeight / 150);
                        //拿取耗材包數:
                        if (PrePacketCount > NowPacketCount) {
                            int GetPacketCount = PrePacketCount - NowPacketCount;
                            lendItemBridge.consumableUpdatePreborrow(GetPacketCount, ConsumableCabinetID);
                            StockResultData stockResultData = stocktakingBridge.stocktakingConsumable(ConsumableWeight, ConsumableCabinetID);
                            String returnPacketMessage = "01C$name:" + stockResultData.getProductName() + "$amount:" + String.format("%d", stockResultData.getStockCount()) + "-";
                            int nodeID = Server.getInstance().sqlConnection.selectNodeIDCLimit1();
                            nodeCluster.getInstance().getNode(nodeID).dataChannel.ProcessSend(returnPacketMessage);
                        } else {
                            StockResultData stockResultData = stocktakingBridge.stocktakingConsumable(ConsumableWeight, ConsumableCabinetID);
                        }
                    } else {
                        throw new InvalidPacketException("The count of packet is only three. Invalid packet format for consumable.");
                    }
                    break;

                //  03C:barcode:RFID (貴重: two packets message)
                case "E":
                    System.out.println("收到交易封包(Access)，設備類型:" + packetValueInstance.getPacketType() + "，封包內容:" + packetValueInstance.getPacketMessage());
                    if (packetValueInstance.getPacketMessage().size() == 2) {
                        String ExpensiveBarcode = packetValueInstance.getPacketMessage().get(0);
                        String UserRFID = packetValueInstance.getPacketMessage().get(1);
                        StockResultData stockResultData = stocktakingBridge.stocktakingExpensive(ExpensiveBarcode, UserRFID);
                        String returnPacketMessage = "01E$name:" + stockResultData.getProductName() + "$amount:" + String.format("%d", stockResultData.getStockCount()) + "-";
                        int nodeID = Server.getInstance().sqlConnection.selectNodeIDELimit1();
                        nodeCluster.getInstance().getNode(nodeID).dataChannel.ProcessSend(returnPacketMessage);
                    } else {
                        throw new InvalidPacketException("The count of packet is only two. Invalid packet format for expensive.");
                    }
            }
        }
        // 封包類型:02
        if (packetValueInstance.getPacketCategory() == 2) {
            String packetMessageString = packetValueInstance.getPacketMessage().get(0);
            // 二進制字串轉十進制
            int deviceID = Integer.parseInt(packetMessageString, 2);
            switch (packetValueInstance.getPacketType()) {
                case "A":
                    System.out.println("Access: nodeDeviceIndex.get():" + nodeDeviceIndex.get());
                    System.out.println("收到開始封包(Access)，設備類型:" + packetValueInstance.getPacketType() + "，設備ID:" + deviceID + "，nodeID:" + nodeCluster.instance.lastNodeID);
                    Server.getInstance().sqlConnection.addNodeDevice(deviceID, packetValueInstance.getPacketType(), nodeDeviceIndex.get());
                    nodeDeviceIndex.getAndAdd(1);
                    break;

                case "L":
                    System.out.println("LED: nodeDeviceIndex.get():" + nodeDeviceIndex.get());
                    System.out.println("收到開始封包(LED)，設備類型:" + packetValueInstance.getPacketType() + "，設備ID:" + deviceID + "，nodeID:" + nodeCluster.instance.lastNodeID);
                    Server.getInstance().sqlConnection.addNodeDevice(deviceID, packetValueInstance.getPacketType(), nodeDeviceIndex.get());
                    nodeDeviceIndex.getAndAdd(1);
                    break;

                case "C":
                    System.out.println("Consumable: nodeDeviceIndex.get():" + nodeDeviceIndex.get());
                    System.out.println("收到開始封包(Consumable)，設備類型:" + packetValueInstance.getPacketType() + "，設備ID:" + deviceID + "，nodeID:" + nodeCluster.instance.lastNodeID);
                    Server.getInstance().sqlConnection.addNodeDevice(deviceID, packetValueInstance.getPacketType(), nodeDeviceIndex.get());
                    nodeDeviceIndex.getAndAdd(1);
                    break;

                case "E":
                    System.out.println("Expensive: nodeDeviceIndex.get():" + nodeDeviceIndex.get());
                    System.out.println("收到開始封包(Expensive)，設備類型:" + packetValueInstance.getPacketType() + "，設備ID:" + deviceID + "，nodeID:" + nodeCluster.instance.lastNodeID);
                    Server.getInstance().sqlConnection.addNodeDevice(deviceID, packetValueInstance.getPacketType(), nodeDeviceIndex.get());
                    nodeDeviceIndex.getAndAdd(1);
                    break;
            }
        }

//            if (c.equalsIgnoreCase("rfid")){
//                this.instance.setLastRFID(result);
//            }
//            if (c.equalsIgnoreCase("consum")){
//                lendItemBridge.addItem2(this.instance.RFID,result);
//            }
//            if (c.equalsIgnoreCase("barcode")){
//                System.out.println(this.instance + " RFID: " + this.instance.RFID + " get BARCODE " + result);
//          //       this should be lend.
//                lendItemBridge.addItem(result,this.instance.RFID);
//            }

        //0315 比賽用
//            if(c.equalsIgnoreCase("11263137")){
//                SQLConnection sc1 = new SQLConnection();
//                int nodeNumber = sc1.searchLastQRCodeNum();
//                System.out.println(this.instance + " enable QR CODE: " + result);
//                QRCodeBridge.codeEnable(nodeNumber);
//            }
        //0315 比賽用

//            if (c.equalsIgnoreCase("id")){
//                int num = 0;
//                try{
//                    num = Integer.parseInt(result);
//                }catch(Exception e){
//                    System.out.println(this.instance + " err:" + e.getMessage());
//
//                }
//                this.instance.setDeviceID(num);
//            }else if (c.equalsIgnoreCase("type")){
//                int num = 0;
//                try{
//                    num = Integer.parseInt(result);
//                }catch(Exception e){
//                    System.out.println(this.instance + " err:" + e.getMessage());
//                }
//                this.instance.setType(nodeType.getFromID(num));
//            }
    }


    /**
     * call this when need to send data to the socket
     */


    public void ProcessSend(cmd message) {
        sendThread.sendMessage(message.toString());
    }

    public void ProcessSend(String message) {
        sendThread.sendMessage(message);
    }

    @Override
    public String toString() {
        return this.instance.toString();
    }
}