package gjt.usblab.nodeCluster.connectionChannel;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.nodeCluster.node;

public class DataChannel extends BaseConnectionChannel{
    private boolean channelStop = false;
    public DataChannel(baseSocket cSocket,node instance) {
        super(cSocket, instance);
        
    }

    public void closeChannel(){
        channelStop = true;
    }

    public void waitUntiChannelStopped(){
        while(true){
            if(channelStopped()) break;
            try{

                Thread.sleep(100);
            }catch(Exception e){

            }
        }
    }

    public boolean channelStopped(){
        return channelStop;
    }


    

    
    public void ProcessRecv(BasePacket p) {
        // System.out.println("?");
        // System.out.println(this);
        
    }


    
    public void disconnect(){
        synchronized(this){
            if (!terminated){
                terminated = true;
                recvThread.terminate();
                sendThread.terminate();
                connectionSocket.terminate();
            }
            
        }
    }
}