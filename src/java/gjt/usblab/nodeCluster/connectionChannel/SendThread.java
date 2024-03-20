package gjt.usblab.nodeCluster.connectionChannel;

import java.util.LinkedList;
import java.util.Queue;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.utils.logger;

public class SendThread implements Runnable{
    private BaseConnectionChannel instance;
    private baseSocket socket;
    private Queue<String > message_queue = new LinkedList<String>();
    private volatile boolean isContinue = true;
    public SendThread(baseSocket socket,BaseConnectionChannel ins){
        this.socket = socket;
        this.instance = ins;
    }

    public void terminate() {
        isContinue = false;
    }

    public void run(){
        while(isContinue){
            // do something
            if (!message_queue.isEmpty()){
                // pop
                String message = message_queue.poll();
                try{
                    Thread.sleep(1);
                }catch(Exception e){
    
                }
                if (message == null) {
                    // System.out.println(message_queue.size());
                    // System.out.println(message_queue.peek());
                    continue;
                }
                socket.sendMessage(message+"\r\n");
                System.out.println(this.instance + " send:"+message);
                //logger.getInstance().node_log("packet sent\n"+message,instance.instance);
            }
        }
    }
    public void sendMessage(String message){
        message_queue.add(message);
    }
    /*public void sendPacket(BasePacket packet){
        message_queue.add(packet);
    }*/
}
