package gjt.usblab.SQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import gjt.usblab.data.userData;

public class SQLConnection {

    //0315 比賽用
    public int searchLastQRCodeNum(){
        try{
            int LastQRCodeNum = 1;
            String query = "SELECT MAX(QRCodeNo) AS max_id FROM qrcode";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                LastQRCodeNum = rs.getInt("max_id");
            }
            rs.close();
            state.close();
            connection.close();
            return LastQRCodeNum;
        }catch(Exception e){
            System.out.println("Error for search Last QRCode Number.");
            return -1;
        }
    }
    //0315 比賽用

    public boolean addTaken(int eNo,int CNo){
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement lend = connection.prepareStatement("INSERT INTO Taken (TNo,eNo,CNo,count) VALUES (?,?,?,?)");
            //System.out.println( player.getUniqueId());

            lend.setNull(1,Types.INTEGER);//a_i
            lend.setInt(2, eNo);
            lend.setInt(3, CNo);
            lend.setInt(4, 1);

            lend.executeUpdate();
            lend.close();

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
            
            
        }
    }

    public boolean addLend(int eNo,int sNo){
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement lend = connection.prepareStatement("INSERT INTO Lend (INo,eNo,sNo) VALUES (?,?,?)");
            //System.out.println( player.getUniqueId());

            lend.setNull(1,Types.INTEGER);//a_i
            lend.setInt(2, eNo);
            lend.setInt(3, sNo);

            lend.executeUpdate();
            lend.close();

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
            
            
        }
    }

    public int addQRCode(String code){
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement qrCode = connection.prepareStatement("INSERT INTO QRCode (QRCodeNo,Info) VALUES (?,?)");
            //System.out.println( player.getUniqueId());

            qrCode.setNull(1,Types.INTEGER);//a_i
            qrCode.setString(2, code);

            qrCode.executeUpdate();
            qrCode.close();

            //serach id

            String query = "SELECT * FROM QRCode WHERE Info = '"+code+"'";
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            rs.next();
            int no = rs.getInt("QRCodeNo");
            rs.close();
            state.close();
            connection.close();
            return no;
        }catch(Exception e){
            e.printStackTrace();
            return -1;
            
            
        }
    }

    public boolean reigsterAccount(String account,String password){
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement newaccount = connection.prepareStatement("INSERT INTO Employee (eNo,Name,account,password) VALUES (?,?,?,?)");
            //System.out.println( player.getUniqueId());
            newaccount.setNull(1,Types.INTEGER);//a_i
            newaccount.setString(0, password);

            newaccount.executeUpdate();
            newaccount.close();

            return true;
        }catch(Exception e){
            return false;
        }
    }

    public ArrayList<HashMap<String,Object>> cmdFetchData(String command,String... elements){
        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
        try{
            String get_e = "";
            boolean b = false;
            for (String s:elements){
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = command;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            while(rs.next()){
                HashMap<String,Object> mp = new HashMap<>();
                for (String s:elements){
                    mp.put(s, rs.getObject(s));
                }
                result.add(mp);
            }
            rs.close();
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }



    public ArrayList<HashMap<String,Object>> fetchData(String table,String condition,String... elements){
        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
        try{
            String get_e = "";
            boolean b = false;
            for (String s:elements){
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = "SELECT "+get_e+" FROM "+table+" WHERE "+condition;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            while(rs.next()){
                HashMap<String,Object> mp = new HashMap<>();
                for (String s:elements){
                    mp.put(s, rs.getObject(s));
                }
                result.add(mp);
            }
            rs.close();
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    public int getAvailableItem(int RegisterItemID){
        int ava = 0;
        // 對所有 於註冊物品 中的 庫存 在 Lend 中的 LendDate 為 非 null 
        try{
            String query = "SELECT * FROM RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo INNER JOIN Lend on Storage.sNo = Lend.sNo Where Lend.ReturnDate IS NULL AND RegisterItem.RiNo = "+RegisterItemID + " group by Storage.sNo;";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            rs.last();
            int len = rs.getRow();
            rs.close();
            state.close();

            query = "SELECT * FROM RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo Where RegisterItem.RiNo = "+RegisterItemID;
            state = connection.createStatement();
            rs = state.executeQuery(query);
            rs.last();
            int sz = rs.getInt("count");
            rs.close();
            state.close();

//            ava = sz-len;

            // 0315 比賽用，上面 214行 記得比賽完開啟。
            ava = sz;
            // 0315 比賽用

            connection.close();
        }catch(Exception e){

        }
        return ava;
    }
    public HashMap<String,Object> fetchSingleData(String table,String condition,String... elements){
        HashMap<String,Object> result = new HashMap<String,Object>();
        try{
            String get_e = "";
            boolean b = false;
            for (String s:elements){
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = "SELECT "+get_e+" FROM "+table+" WHERE "+condition;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if(!rs.next()) return result;
            for (String s:elements){
                result.put(s, rs.getObject(s));
            }
            rs.close();
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void updatePBItemWithQRCode(int eNo,int QRCode){
        String query = "UPDATE PreBorrow SET QRCodeNo= '"+QRCode+"' WHERE eNo = '"+eNo+"' AND QRCodeNo IS NULL ";
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updatePBItemHoldCount(int eNo,int QRCode,int PbNo, int HoldCount){
        String query = "UPDATE PreBorrow SET HoldCount= '"+HoldCount+"' WHERE eNo = '"+eNo+"' AND QRCodeNo = '"+QRCode+"' AND PbNo = '"+PbNo+"'";
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateConsumbleCount(int CNo,int count){
        String query = "UPDATE Consumables SET count= '"+count+"' WHERE CNo = '"+CNo+"'";
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updatePBItem(int eNo,int RiNo,int cnt,int PbNo){
        String query = "UPDATE PreBorrow SET PbCount='"+cnt+"' WHERE eNo = '"+eNo+"' AND RiNo = '"+RiNo+"' AND PbNo = '"+PbNo+"' AND QRCodeNo IS NULL";
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deletePBItem(int eNo,int RiNo,int PbNo){
        String query = "DELETE FROM PreBorrow WHERE eNo = '"+eNo+"' AND RiNo = '"+RiNo+"' AND PbNo = '"+PbNo+"' AND QRCodeNo IS NULL";
        try{
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void lendItem(int eNo,int RiNo,int cnt){
        ArrayList<HashMap<String,Object>> ret = this.cmdFetchData(
            "SELECT * FROM PreBorrow WHERE eNo = '"+eNo+"' AND RiNo = '"+RiNo+"' AND QRCodeNo IS NULL",
             "PbNo",
             "PbCount");
        if (ret.size() > 0){ // find one have null qrcode (un send)
            //update
            HashMap<String,Object> mp = ret.get(0);
            int pbno = (int)mp.get("PbNo");
            int pbcount = (int)mp.get("PbCount");
            pbcount = pbcount + cnt;
            String query = "UPDATE PreBorrow SET PbCount='"+pbcount+"' WHERE eNo = '"+eNo+"' AND RiNo = '"+RiNo+"' AND PbNo = '"+pbno+"' AND QRCodeNo IS NULL";
            try{
                Connection connection = DatabaseConnection.getConnection();
                Statement state = connection.createStatement();
                state.executeUpdate(query);
                state.close();
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            // insert
            try{

                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement newBorrow = connection.prepareStatement("INSERT INTO PreBorrow (PbNo,eNo,RiNo,PbCount,HoldCount) VALUES (?,?,?,?,?)");
                //System.out.println( player.getUniqueId());
                newBorrow.setNull(1,Types.INTEGER);//a_i
                newBorrow.setInt(2, eNo);
                newBorrow.setInt(3, RiNo);
                newBorrow.setInt(4, cnt);
                newBorrow.setInt(5, 0);

                newBorrow.executeUpdate();
                newBorrow.close();

            }catch(Exception e){
                e.printStackTrace();

            }
        }
    }


    public userData authenticate(String account,String password){
        try{
            String query = "SELECT eNo,Name,RFIDNo FROM Employee WHERE account = '"+account+"'" + " AND "+" password='"+password+"'";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            int result;
            String name;
            String RFID;
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            result = rs.getInt("eNo");
            name = rs.getString("Name");
            RFID = rs.getString("RFIDNo");
            userData data = new userData(result, name, RFID);
            rs.close();
            state.close();
            connection.close();
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
