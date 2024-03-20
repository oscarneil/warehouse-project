package gjt.usblab.nodeCluster;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.formatted.GreetPacket;
import gjt.usblab.nodeCluster.connectionChannel.DataChannel;
import gjt.usblab.utils.logger;

public class node {
    private String IP;
    private int id;
    private baseSocket csocket;
    private baseSocket dataChannelSocket;
    public DataChannel dataChannel;
    private boolean absorb = false;
    public String RFID;
    public nodeType type = nodeType.none;
    public int device_id = -1;
    
    
    public node(String ip,int id,baseSocket cSocket){
        this.IP = ip;
        this.id = id;
        this.csocket = cSocket;
        // after connection the server will give a socket (cSocket) that can be used as DataChannel
        this.dataChannel = new DataChannel(this.csocket,this); // this will create a conneciton that handle
        // 傳送 socket value值
        // dataChannel.ProcessSend("abc");
    }

    public void setType(nodeType type){
        this.type = type;
    }
    public void setDeviceID(int d){
        this.device_id = d;

    }

    public void setLastRFID(String RFID){
        this.RFID = RFID;
    }
    
    

    

    public String getIP(){
        return IP;
    }
    public int getID(){
        return id;
    }


    

    /**
     * if control channel is dead, it means the connection is closed \so call this absorb function to return the data port to the port pool
     */
    public void absorb(){
        
        synchronized(this){
            if (!absorb){
                absorb = true;
                if (dataChannel != null) dataChannel.disconnect();
                nodeCluster.getInstance().removeNode(id);
                // if disconnect data restore 
            }
            
        }
        
    }
    @Override
    public String toString(){
        return "("+this.type.s+") ID: "+device_id+" >";
        
    }
}
