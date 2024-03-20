package gjt.usblab.nodeCluster.connectionChannel;

import java.io.UnsupportedEncodingException;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.utils.logger;

public class RecvThread implements Runnable{
    private BaseConnectionChannel instance;
    private baseSocket socket;
    private volatile boolean isContinue = true;

    public RecvThread(baseSocket socket,BaseConnectionChannel ins){
        this.socket = socket;
        this.instance = ins;
    }

    public void terminate() {
        isContinue = false;
    }

    public void run(){
        String temp = "";
        while (isContinue){
            if (socket.isClosed()){
                System.out.println(this.instance + " disconnect");
                //logger.getInstance().log("client "+instance.instance.getID()+" disconnect..!");
                //logger.getInstance().node_log("client disconnect..!",instance.instance);
                instance.disconnect();
                break;
            }
            //logger.getInstance().log("listening message..!");
            //byte[] data = socket.recvRawData();
            //byte[] data = socket.recvRawData();
            
            //BasePacket packet = socket.recvPacket();
            byte[] packet = socket.recvOneByte();


            if (packet == null || packet == null){
                // should be disconnect?
                
                System.out.println(this.instance + " disconnect");
                //logger.getInstance().log("client "+instance.instance.getID()+" disconnect..!");
                //logger.getInstance().node_log("client disconnect..!",instance.instance);
                instance.disconnect();
                break;
            }else{
                String s;
                try {
                    s = new String(packet, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    s = "";
                }
                if (s.equalsIgnoreCase("-")){

                    System.out.println(this.instance + " recv:"+temp);
                    this.instance.Recv(temp);
                    temp = "";
                }else{

                     temp  += s;

//                    System.out.println(this.instance + " recv:"+s);
                }
                
                
                //this.instance.ProcessRecv(packet);
            }
            
        }
    }
}