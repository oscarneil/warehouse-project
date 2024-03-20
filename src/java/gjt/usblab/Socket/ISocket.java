package gjt.usblab.Socket;
import java.util.List;

import gjt.usblab.Socket.Packet.*;
public interface ISocket {
    
    public abstract byte[] recvRawData();
    
    public abstract boolean sendPacket(BasePacket packet);
    public abstract BasePacket recvPacket();
    public abstract void sendRawData(List<Byte> data);
    public abstract void sendRawData(byte[] data);
    public abstract int getSocketID();
    public abstract String getIP();
    
    
}