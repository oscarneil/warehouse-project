package gjt.usblab.bridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import gjt.usblab.Server.Server;
import gjt.usblab.data.itemData;

public class lendItemBridge {
    public static HashMap<Integer,Queue<itemData>> lendDatas = new HashMap<>();
    public static HashMap<Integer,Integer> qrCodeMap = new HashMap<>();
    public static boolean check(Integer code){
        if (!lendDatas.containsKey(code)){
            lendDatas.put(code, new LinkedList<>());
        
        }
        return !lendDatas.get(code).isEmpty();
    }
    public static itemData get(Integer code){
        return lendDatas.get(code).poll();
    }

    public static void setCode(Integer eNo,Integer code){
        qrCodeMap.put(eNo, code);
    }

    // should from barcode & rfid 
    // rfid get eNo
    // barcode get sNo
    // then put them to Lend (insert)
    // should get code (eNo -> QRCodeNo or just eNo -> code)
    // and update PreBorrow from code
    // for storage 
    public static void addItem(String barcode,String RFID){
        ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
            "SELECT * FROM Employee WHERE RFIDNo = '"+RFID+"'", 
            "eNo"
            );
        if (ret.size() == 0){
            System.out.println("RFID NOT FOUND "+RFID);
            return;
        }

        int eNo = (int )ret.get(0).get("eNo");

        ArrayList<HashMap<String,Object>> ret2 = Server.getInstance().sqlConnection.cmdFetchData(
            "SELECT * FROM Storage INNER JOIN BarCode on Storage.BCNo = BarCode.BCNo WHERE Info = '"+barcode+"'", 
            "sNo",
            "Name"
        );
        if (ret2.size() == 0){
            System.out.println("BAR CODE NOT FOUND "+barcode);
            return;
        }
        
        int sNo = (int )ret2.get(0).get("sNo");

        String Name = (String )ret2.get(0).get("Name");

        itemData data = new itemData(sNo, Name, barcode);

        if (!qrCodeMap.containsKey(eNo)){
            System.out.println("CODE NOT FOUND ");
            return;
        }

        int code = qrCodeMap.get(eNo);
        // get pre borrow count
        ArrayList<HashMap<String,Object>> ret3 = Server.getInstance().sqlConnection.cmdFetchData(
            "SELECT * FROM `PreBorrow` INNER JOIN Storage on PreBorrow.RiNo = Storage.RiNo  WHERE QRCodeNo = '"+code+"' AND eNo = '"+eNo+"' AND sNo = '"+sNo+"'", 
            "PbNo",
            "HoldCount"
        );
        int PbNo = (int)ret3.get(0).get("PbNo");
        int holdcount = (int)ret3.get(0).get("HoldCount");

        // post process

        if (!lendDatas.containsKey(code)) lendDatas.put(code, new LinkedList<>());
        
        lendDatas.get(code).add(data);

        // insert to data
        Server.getInstance().sqlConnection.addLend(eNo, sNo);

        // update preborrow
        Server.getInstance().sqlConnection.updatePBItemHoldCount(eNo,code,PbNo,holdcount+1);
        System.out.println("OK");
    }

    // for consumables
    // when change weight 
    static Object lock = new Object();
    public static void addItem2(String RFID,String shID){
        synchronized(lock){
            ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT * FROM Employee WHERE RFIDNo = '"+RFID+"'", 
                "eNo"
                );
            if (ret.size() == 0){
                System.out.println("RFID NOT FOUND "+RFID);
                return;
            }

            int eNo = (int )ret.get(0).get("eNo");

            ArrayList<HashMap<String,Object>> ret2 = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT * FROM `RegisterItem` INNER JOIN Shelf on RegisterItem.shNo = Shelf.shNo INNER JOIN Consumables on Consumables.RiNo = RegisterItem.RiNo WHERE shID = '"+shID+"'", 
                "CNo",
                "Name",
                "count"
            );
            if (ret2.size() == 0){
                System.out.println("shID NOT FOUND "+shID);
                return;
            }
            
            int cNo = (int )ret2.get(0).get("CNo");
            int itemLeft = (int) ret2.get(0).get("count"); 
            String Name = (String )ret2.get(0).get("Name");

            itemData data = new itemData(cNo, Name, "(消耗品)");

            if (!qrCodeMap.containsKey(eNo)){
                System.out.println("CODE NOT FOUND ");
                return;
            }

            int code = qrCodeMap.get(eNo);
            // get pre borrow count
            ArrayList<HashMap<String,Object>> ret3 = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT * FROM `PreBorrow` INNER JOIN Consumables on PreBorrow.RiNo = Consumables.RiNo WHERE QRCodeNo = '"+code+"' AND eNo = '"+eNo+"' AND CNo = '"+cNo+"'", 
                "PbNo",
                "HoldCount"
            );
            int PbNo = (int)ret3.get(0).get("PbNo");
            int holdcount = (int)ret3.get(0).get("HoldCount");

            // post process

            if (!lendDatas.containsKey(code)) lendDatas.put(code, new LinkedList<>());
            
            lendDatas.get(code).add(data);

            // insert to data
            Server.getInstance().sqlConnection.addTaken(eNo, cNo);

            // update preborrow
            Server.getInstance().sqlConnection.updatePBItemHoldCount(eNo,code,PbNo,holdcount+1);
            itemLeft--;
            Server.getInstance().sqlConnection.updateConsumbleCount(cNo,itemLeft);
            System.out.println("OK");
        }
    }
}
