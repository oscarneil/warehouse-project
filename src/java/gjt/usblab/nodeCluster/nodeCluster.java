package gjt.usblab.nodeCluster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gjt.usblab.Socket.baseSocket;
import gjt.usblab.utils.logger;
public class nodeCluster {
    public static nodeCluster instance = null;
    public HashMap<Integer,node> nodes = new HashMap<>();
    public int door_node;

    public int lastNodeID;
    
    
    public node getDoorNode(){
        return nodes.get(door_node);
    }


    public static nodeCluster getInstance(){
        if (instance == null) instance = new nodeCluster();
        return instance;

    }
    private nodeCluster(){
    }  
    public void addNode(node node){
        logger.getInstance().log(node.toString());
        
        if (nodes.containsKey(node.getID())){
            System.out.println("collision!");
        }

        System.out.println("目前 nodeID :"+node.getID());
        lastNodeID = node.getID();
        nodes.put(node.getID(),node);
        
    }

    public void removeNode(int id){
        this.nodes.remove(id);
    }

    public node getNode(int id){
        return nodes.get(id);
    }
}