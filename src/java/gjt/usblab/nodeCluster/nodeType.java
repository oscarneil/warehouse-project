package gjt.usblab.nodeCluster;

import java.util.HashMap;

public enum nodeType {
    light("light",1),
    shelf("shelf",2),
    door("door",3),
    none("unkown",4);
    public static HashMap<Integer,nodeType> mp = new HashMap<>();
    public String s;
    public int id;
    
    static{
        for (nodeType type:values()){

            mp.put(type.id, type);
        }

    }
    private nodeType(String s,int n){
        this.s = s;
        this.id = n;
    }
    public static nodeType getFromID(int id){
        return mp.get(id);
    }
}
