package gjt.usblab.Socket.Packet;

public interface IPacket {
    public abstract byte[] getLengthByte();
    public abstract byte[] getData();
    public abstract byte[] getPacketID();
    public abstract Object getObject();
    public abstract boolean equals(PacketType type);

}