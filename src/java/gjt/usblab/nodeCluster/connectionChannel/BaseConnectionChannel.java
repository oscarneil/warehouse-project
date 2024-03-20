package gjt.usblab.nodeCluster.connectionChannel;

import java.util.ArrayList;
import java.util.HashMap;

import gjt.usblab.Server.Server;
import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.Socket.Packet.cmd;
import gjt.usblab.Socket.Packet.formatted.GreetPacket;
import gjt.usblab.bridge.QRCodeBridge;
import gjt.usblab.bridge.lendItemBridge;
import gjt.usblab.nodeCluster.node;
import gjt.usblab.nodeCluster.nodeType;
import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.utils.logger;

public abstract class BaseConnectionChannel {

    protected node instance;
    protected RecvThread recvThread;
    protected SendThread sendThread;
    protected baseSocket connectionSocket;
    protected boolean terminated = false;
    public BaseConnectionChannel(baseSocket cSocket,node instance){
        this.instance = instance;
        this.connectionSocket = cSocket;
        recvThread = new RecvThread(connectionSocket,this);
        sendThread = new SendThread(connectionSocket,this);
        Thread recv = new Thread(recvThread);
        Thread send = new Thread(sendThread);
        //logger.getInstance().log(connectionSocket.toString());
        recv.start();
        send.start();
    }



    // if disconnect
    // to be synchronized if there have alot of thread to disconnect
    public abstract void disconnect();

    /**
     * call this when recv a packet from the socket
     * @param p the packet
     */
    
    public abstract void ProcessRecv(BasePacket p);

    public void Recv(String s){
        if (s.contains(":")){
            String[] splits = s.split(":");
            String c = splits[0];
            String result = splits[1];

            if(c.contains("shelfE")){
                System.out.println("Send Socket Message from BaseConnectionChannel.java : shelfE");
                this.instance.dataChannel.ProcessSend("NAME:Knives-AMOUNT:5-");
            }

            if(c.contains("shelfC")){
                System.out.println("Send Socket Message from BaseConnectionChannel.java : shelfC");
                this.instance.dataChannel.ProcessSend("NAME:Consumables-AMOUNT:1-");
            }

            if(c.contains("led")){
                System.out.println("Send Socket Message from BaseConnectionChannel.java : led");
                this.instance.dataChannel.ProcessSend("REDON-");
                try{
                    System.out.println("Thread sleep start 10sec");
                    Thread.sleep(10*1000);
                    System.out.println("Thread wakeup");
                }catch(InterruptedException e){
                    System.out.println("Exception while Thread sleep:"+e.toString());
                }
                this.instance.dataChannel.ProcessSend("REDOFF-");
            }

            if (c.equalsIgnoreCase("rfid")){
                this.instance.setLastRFID(result);
            }
            if (c.equalsIgnoreCase("consum")){
                lendItemBridge.addItem2(this.instance.RFID,result);
            }
            if (c.equalsIgnoreCase("barcode")){
                System.out.println(this.instance + " RFID: " + this.instance.RFID + " get BARCODE " + result);
          //       this should be lend.
                lendItemBridge.addItem(result,this.instance.RFID);
            }
            if (c.equalsIgnoreCase("qrcode")){
                System.out.println(this.instance + " enable QR CODE: " + result);

                ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                    "SELECT * from QRCode WHERE Info = '"+result+"' ",
                    "QRCodeNo");
                if (ret.size() == 0){
                    System.out.println("code not found!");
                    return;
                }
                int QRCodeNo = (int)ret.get(0).get("QRCodeNo");
                QRCodeBridge.codeEnable(QRCodeNo);
            }

            //0315 比賽用
            if(c.equalsIgnoreCase("11263137")){
                SQLConnection sc1 = new SQLConnection();
                int nodeNumber = sc1.searchLastQRCodeNum();
                System.out.println(this.instance + " enable QR CODE: " + result);
                QRCodeBridge.codeEnable(nodeNumber);
            }
            //0315 比賽用

            if (c.equalsIgnoreCase("id")){
                int num = 0;
                try{
                    num = Integer.parseInt(result);
                }catch(Exception e){
                    System.out.println(this.instance + " err:" + e.getMessage());
                    
                }
                this.instance.setDeviceID(num);
                /*
                try{

                    if(this.instance.type == nodeType.shelf){
                        Thread.sleep(3000);
                        ProcessSend(cmd.RED_ON);
                        Thread.sleep(30000);
                        ProcessSend(cmd.RED_OFF);
                        Thread.sleep(30000);
                        ProcessSend(cmd.GREEN_ON);
                        Thread.sleep(30000);
                        ProcessSend(cmd.GREEN_OFF);
                        Thread.sleep(30000);
                        ProcessSend(cmd.YELLOW_OFF);
                        Thread.sleep(30000);
                        ProcessSend(cmd.YELLOW_ON);
                        Thread.sleep(30000);
                    }else if (this.instance.type == nodeType.light){
                        //Thread.sleep(3000);
                        //ProcessSend(cmd.LED_ON);
                        //Thread.sleep(30000);
                        //ProcessSend(cmd.LED_OFF);
                        //Thread.sleep(30000);
                        ProcessSend(String.format( "NAME:%s-COUNT:%s-", "basic01","200"));
                    }


                }catch(Exception e){

                } */

            }else if (c.equalsIgnoreCase("type")){
                int num = 0;
                try{
                    num = Integer.parseInt(result);
                }catch(Exception e){
                    System.out.println(this.instance + " err:" + e.getMessage());
                }
                this.instance.setType(nodeType.getFromID(num));
            }




        }
        

    }


    
    /**
     * call this when need to send data to the socket
     *
     */
    
     
    
     public void ProcessSend(cmd message){
        sendThread.sendMessage(message.toString());
    }
    public void ProcessSend(String message){
        sendThread.sendMessage(message);
    }
    @Override
    public String toString(){
        return this.instance.toString();
    }
}