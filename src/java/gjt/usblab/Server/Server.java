package gjt.usblab.Server;

import java.net.ServerSocket;

import gjt.usblab.SQLConnection.SQLConnection;
import gjt.usblab.Socket.baseSocket;
import gjt.usblab.Socket.serverSocket;
import gjt.usblab.nodeCluster.*;
import gjt.usblab.utils.logger;
import gjt.usblab.utils.logger.LogLevel;;

public class Server {
    private static Server instance = null;
    private ServerSocket server;
    private int port = 8764;
    private serverSocket serverSocket;
    public boolean listen_success = false;
    private nodeCluster cluster;
    public SQLConnection sqlConnection;
    private Server(){
        cluster = nodeCluster.getInstance();
        this.sqlConnection = new SQLConnection();
    }
    public static Server getInstance(){
        if (instance == null) instance = new Server();
        return instance;
    }

    public void setup(){
        try{
            server = new ServerSocket(port);
            listen_success = true;
            serverSocket = new serverSocket(server);
            logger.getInstance().log("port bind successful");
        }catch(Exception e){
            logger.getInstance().log("port bind failed : " + e.getMessage(),LogLevel.ERROR);
        }
        
    }

    public void shutdown(){
        try{
            this.server.close();
        }catch(Exception e){
            
        }
    }


    public void running(){
        System.out.println("start running");
        while (true){
            baseSocket conn;
            synchronized (serverSocket){
                logger.getInstance().log("waiting for connection");
                conn = serverSocket.getConnection();
                if (conn != null){
                    node node = new node(conn.getIP(),conn.getSocketID(),conn); // create a connection object
                    System.out.println(node + " connected");
                    cluster.addNode(node);
                    logger.getInstance().log(conn.toString());
                    logger.getInstance().log(node.toString());
                    // last = connection.getId();
                    logger.getInstance().log("machine "+node.getIP()+" connected!");
                }

            }

        }

    }

}
