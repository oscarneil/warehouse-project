package gjt.usblab.Socket.Packet;

import java.util.HashMap;

public enum PacketType {
    greet(1);
    
    private static HashMap<Integer,PacketType> mp = new HashMap();
    static{
        for (PacketType type :values()){
            mp.put(type.ID, type);
        }
    }
    private int ID;
    private PacketType(int ID){
        this.ID = ID;
    }
    public byte[] getByteID(){
        byte[] b = new byte[2];
        b[0] = (byte) ((ID/10) + 48);
        b[1] = (byte) ((ID%10) + 48);
        return b;
    }
    public static PacketType getType(byte[] b){
        String s = new String(b); // maybe 2 digits
        int id = 0;
        for (int i = 0 ; i < s.length();i++){
            id = id * 10 + s.charAt(i) - '0';
        }

        return mp.get(id);
    }
}