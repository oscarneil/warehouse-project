package gjt.usblab.Socket;

import java.net.ServerSocket;

public class serverSocket{
    private ServerSocket socket;
    private int socketIdCounter= 0;
    
    public serverSocket(ServerSocket socket){
        this.socket = socket;
    }
    public baseSocket getConnection(){
        baseSocket cs;
        try{
            cs = new baseSocket(socket.accept(),socketIdCounter);
            //logger.getInstance().log(cs.toString());
            socketIdCounter+=1;
        }catch(Exception e){
            return null;
        }
        return cs;
    }
    /**
     * socket bind port 
     * @param port
     * @return if successful
     */
    public boolean bind(int port){
        try{
            socket = new ServerSocket(port);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
