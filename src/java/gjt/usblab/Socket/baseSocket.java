package gjt.usblab.Socket;

import java.util.List;

import gjt.usblab.Socket.Packet.*;
import gjt.usblab.Socket.Packet.BasePacket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class baseSocket implements ISocket {

    public int id;
    private BufferedInputStream in;
    private BufferedOutputStream out;
    private Socket socket;
    public baseSocket(Socket socket,int id){
        this.socket = socket;
        this.id = id;
        try{
            out = new BufferedOutputStream(socket.getOutputStream());
            in = new BufferedInputStream(socket.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void terminate(){
        try{
            out.close();
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean isClosed(){
        return socket.isClosed();
    }
    
    
    
    public byte[] recvRawData() {
        try{
            byte[] b = new byte[1024];
            int length = in.read(b);
            System.out.println("legnth:"+length);
            for (int i = 0 ; i < length;i++){
                System.out.println(b[i]);
            }
            if (length <= -1) return null;
            return b;
        }catch(Exception e){
            return null;
        }
        
    }

    
    public void sendRawData(List<Byte> data) {
        try{
            byte[] cbyte = new byte[data.size()];
            int j = 0;
            for (Byte b: data){
                cbyte[j++] = b.byteValue();
            }
            
            out.write(cbyte);
            out.flush();
        }catch(Exception e){

        }
    }

    
    public void sendRawData(byte[] data) {
        try{
            out.write(data);
            out.flush();
        }catch(Exception e){

        }
    }
    
    public int getSocketID() {
        // TODO Auto-generated method stub
        return id;
    }
    
    public String getIP() {
        // TODO Auto-generated method stub
        return socket.getInetAddress().getHostAddress();
    }
    public int getID(){
        return id;
    }

    public boolean sendMessage(String s){

        try{
            
            out.write(s.getBytes());
            out.flush();
            return false;
        }catch(Exception e){
            return true;
        }
    }


    
    public boolean sendPacket(BasePacket packet) {
        try{
            byte[] length = packet.getLengthByte();
            out.write(length);
            byte[] PacketID = packet.getPacketID();
            out.write(PacketID);
            byte[] Data = packet.getData();
            out.write(Data);
            out.flush();
            return false;
        }catch(Exception e){
            return true;
        }
    }

    public byte[] recvOneByte(){
        byte[] length = new byte[1];
        int i_length = recvData(length,1);
        if (i_length == -1) return null;
        return length;
    }
    
    public BasePacket recvPacket() {
        byte[] length = new byte[7];
        int i_length = recvLength(length);
        if (i_length == -1) return null;
        byte[] PacketID = new byte[2];
        int i_PacketID = recvPacketID(PacketID);
        if (i_PacketID == -1) return null;
        // calc data length
        int dataLength = i_length - 7 - 2;
        
        byte[] Data = new byte[dataLength];
        int ret = recvData(Data, dataLength);
        if (ret == -1) return null;


        System.out.println("packet created");
        return PacketUtils.createPacket(length, PacketID, Data);

    }

    int recvLength(byte[] ref){
        int length = 0;
        try{
            byte[] b = new byte[7];
            int dataLength = in.read(b);
            for (int i = 0 ; i < 7;i++){
                ref[i] = b[i];
                char num = (char) b[i];
                length = length * 10 + (num-'0');
            }
            if (dataLength <= 0) return -1;




            return length;
        }catch(Exception e){
            return -1; // error
        }
    }

    int recvPacketID(byte[] ref){
        int packetid = 0;
        try{
            byte[] b = new byte[2];
            int dataLength = in.read(b);
            for (int i = 0 ; i < 2;i++){
                ref[i] = b[i];
                char num = (char) b[i];
                packetid = packetid * 10 + (num-'0');
            }
            if (dataLength <= 0) return -1;




            return packetid;
        }catch(Exception e){
            return -1; // error
        }
    }

    int recvData(byte[] ref,int length){
        if (length == 0) return 1;
        try{
            byte[] b = new byte[length];
            int dataLength = in.read(b);
            for (int i = 0 ; i < length;i++){
                ref[i] = b[i];
            }
            if (dataLength <= 0) return -1;




            return 1;
        }catch(Exception e){
            return -1; // error
        }
    }
    
}
