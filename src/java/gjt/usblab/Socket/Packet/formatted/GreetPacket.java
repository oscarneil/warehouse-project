package gjt.usblab.Socket.Packet.formatted;

import gjt.usblab.Socket.Packet.BasePacket;
import gjt.usblab.Socket.Packet.PacketType;

public class GreetPacket extends BasePacket{
    public final PacketType packetType = PacketType.greet;
    
    public GreetPacket(BasePacket bp){
        data = bp.data;
        length = bp.length;
        packetid = bp.packetid;
    }

    public GreetPacket(){
        super();
    }
    public GreetPacket(String message){
        packetid = packetType.getByteID();
        
        data = new byte[message.length()];
        int ind = 0;
        for(char c: message.toCharArray()){
            data[ind++] = (byte) c;
        }

        
        length = getLength(7+2+data.length);

    }

    @Override
    public Object getObject(){

        return new String(data);
    }
    @Override
    public boolean equals(PacketType type){
        return this.packetType.equals(type);
    }
}