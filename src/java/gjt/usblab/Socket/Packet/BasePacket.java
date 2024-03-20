package gjt.usblab.Socket.Packet;

import gjt.usblab.Socket.Packet.*;


public class BasePacket implements IPacket{
    public PacketType packetType;
    public byte[] length;
    public byte[] packetid;
    public byte[] data;
    
    public byte[] getLengthByte() {
        return length;
    }

    
    public byte[] getData() {
        return data;
    }

    
    public byte[] getPacketID() {
        return packetid;
    }

    
    public BasePacket(){
        
    }
    public BasePacket(PacketType type){
        this.packetType = type;
    }
    public BasePacket(byte[] a,byte[] b,byte[] c){
        length = a;
        packetid = b;
        data = c;
        packetType = PacketType.getType(b);
        System.out.println(packetType);
    }

    
    public Object getObject() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObject'");
    }

    public byte[] getLength(int length){
        byte[] b = new byte[7];
        for(int i = 6 ; i >= 0 ; i--){
            int n = length % 10 ;
            b[i] = (byte) (n+48);
            length /= 10;
        }
        return b;
    }

    
    public String toString(){
        String out = new String(length) + "|" + new String(packetid) + "| data length:" + data.length;
        return out;
    }

    
    public boolean equals(PacketType type) {
        return this.packetType.equals(type);
    }


}
