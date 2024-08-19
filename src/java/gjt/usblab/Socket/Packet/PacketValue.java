package gjt.usblab.Socket.Packet;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class PacketValue {
    private static volatile PacketValue instance = null;

    private PacketValue() {
    }

    private String receivePacket;
    private int packetCategory;
    private String[] splitPackets;
    private String packetTitle;
    private String packetType;
    private ArrayList<String> packetMessage;


    public static PacketValue getInstance() {
        if (instance == null) {
            synchronized (PacketValue.class) {
                if (instance == null) {
                    instance = new PacketValue();
                }
            }

        }
        return instance;
    }

    public ArrayList<String> getPacketMessage() {
        return packetMessage;
    }

    public String getPacketType() {
        return packetType;
    }

    public int getPacketCategory() {
        return packetCategory;
    }

    public void setReceivePacket(String receivePacket) {
        this.receivePacket = receivePacket;


        if (!Objects.isNull(this.splitPackets) && this.splitPackets.length != 0) {
            this.splitPackets = new String[1];
        }
        if (Objects.isNull(this.packetMessage)) {
            this.packetMessage = new ArrayList<String>();
        } else {
            this.packetMessage.clear();
        }

        if (receivePacket.contains(":")) {
            this.packetCategory = 3;
            this.splitPackets = this.receivePacket.split(":");
            this.packetTitle = splitPackets[0];
            this.packetType = packetTitle.substring(2, 3);
            Collections.addAll(packetMessage, splitPackets);
            packetMessage.remove(0);
        } else {
            this.packetCategory = 2;
            this.packetTitle = this.receivePacket.substring(0, 2);
            this.packetType = this.receivePacket.substring(2, 3);
            Collections.addAll(packetMessage, this.receivePacket.substring(3));
        }
    }
}
