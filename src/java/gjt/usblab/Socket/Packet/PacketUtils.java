package gjt.usblab.Socket.Packet;

public class PacketUtils {
    
    public static BasePacket createPacket(byte []a,byte []b,byte []c){
        
        return new BasePacket(a, b, c);
    }
}