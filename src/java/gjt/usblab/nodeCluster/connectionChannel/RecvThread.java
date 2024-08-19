package gjt.usblab.nodeCluster.connectionChannel;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.utils.logger;

public class RecvThread implements Runnable {
    private BaseConnectionChannel instance;
    private final baseSocket socket;
    private volatile boolean isContinue = true;
    private final Object lock = new Object();
    private CountDownLatch latch;

    public RecvThread(baseSocket socket, BaseConnectionChannel ins, CountDownLatch latch) {
        this.socket = socket;
        this.instance = ins;
        this.latch = latch;
    }

    public void terminate() {
        isContinue = false;
    }

    public void run() {
        String temp = "";
        while (isContinue) {
            synchronized (socket) {
                if (socket.isClosed()) {
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


                if (packet == null || packet == null) {
                    // should be disconnect?

                    System.out.println(this.instance + " disconnect");
                    //logger.getInstance().log("client "+instance.instance.getID()+" disconnect..!");
                    //logger.getInstance().node_log("client disconnect..!",instance.instance);
                    instance.disconnect();
                    break;
                } else {
                    String s;
                    try {
                        s = new String(packet, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        s = "";
                    }
                    // 每個封包的結束字元都是 "-"
                    if (s.equalsIgnoreCase("-")) {

                        System.out.println(this.instance + " recv:" + temp);

                        try {
                            this.instance.Recv(temp);
                            latch.countDown();
                            temp = "";
                        } catch (Exception e) {
                            System.out.println("Exception while Thread sleep:" + e.toString());
                        }
                    } else {

                        temp += s;

//                    System.out.println(this.instance + " recv:"+s);
                    }


                    //this.instance.ProcessRecv(packet);
                }
            }
        }
    }
}