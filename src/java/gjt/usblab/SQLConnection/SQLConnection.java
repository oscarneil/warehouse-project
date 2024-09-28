package gjt.usblab.SQLConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import gjt.usblab.data.StockResultData;
import gjt.usblab.data.loadLendingData;
import gjt.usblab.data.userData;

public class SQLConnection {

    //0315 比賽用
    public int searchLastQRCodeNum() {
        try {
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
        } catch (Exception e) {
            System.out.println("Error for search Last QRCode Number.");
            return -1;
        }
    }
    //0315 比賽用

    public boolean addTaken(int eNo, int CNo) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement lend = connection.prepareStatement("INSERT INTO Taken (TNo,eNo,CNo,count) VALUES (?,?,?,?)");
            //System.out.println( player.getUniqueId());

            lend.setNull(1, Types.INTEGER);//a_i
            lend.setInt(2, eNo);
            lend.setInt(3, CNo);
            lend.setInt(4, 1);

            lend.executeUpdate();
            lend.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;


        }
    }

    public boolean addLend(int eNo, int sNo) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement lend = connection.prepareStatement("INSERT INTO Lend (INo,eNo,sNo,LendDate) VALUES (?,?,?,now())");
            //System.out.println( player.getUniqueId());

            lend.setNull(1, Types.INTEGER);//a_i
            lend.setInt(2, eNo);
            lend.setInt(3, sNo);

            lend.executeUpdate();
            lend.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;


        }
    }

    public int addQRCode(String code) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement qrCode = connection.prepareStatement("INSERT INTO QRCode (QRCodeNo,Info) VALUES (?,?)");
            //System.out.println( player.getUniqueId());

            qrCode.setNull(1, Types.INTEGER);//a_i
            qrCode.setString(2, code);

            qrCode.executeUpdate();
            qrCode.close();

            //serach id

            String query = "SELECT * FROM QRCode WHERE Info = '" + code + "'";
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            rs.next();
            int no = rs.getInt("QRCodeNo");
            rs.close();
            state.close();
            connection.close();
            return no;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;


        }
    }

    public boolean reigsterAccount(String account, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement newaccount = connection.prepareStatement("INSERT INTO Employee (eNo,Name,account,password) VALUES (?,?,?,?)");
            //System.out.println( player.getUniqueId());
            newaccount.setNull(1, Types.INTEGER);//a_i
            newaccount.setString(0, password);

            newaccount.executeUpdate();
            newaccount.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<HashMap<String, Object>> cmdFetchData(String command, String... elements) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        try {
            String get_e = "";
            boolean b = false;
            for (String s : elements) {
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = command;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                HashMap<String, Object> mp = new HashMap<>();
                for (String s : elements) {
                    mp.put(s, rs.getObject(s));
                }
                result.add(mp);
            }
            rs.close();
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public ArrayList<HashMap<String, Object>> fetchData(String table, String condition, String... elements) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        try {
            String get_e = "";
            boolean b = false;
            for (String s : elements) {
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = "SELECT " + get_e + " FROM " + table + " WHERE " + condition;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            while (rs.next()) {
                HashMap<String, Object> mp = new HashMap<>();
                for (String s : elements) {
                    mp.put(s, rs.getObject(s));
                }
                result.add(mp);
            }
            rs.close();
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getAvailableItem(int RegisterItemID) {
        int ava = 0;
        // 對所有 於註冊物品 中的 庫存 在 Lend 中的 LendDate 為 非 null 
        try {
            String query = "SELECT * FROM RegisterItem INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo INNER JOIN Lend on Storage.sNo = Lend.sNo Where Lend.ReturnDate IS NULL AND RegisterItem.RiNo = " + RegisterItemID + " group by Storage.sNo, Lend.INo;";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            int len = 0;
            while (rs.next()) {
                len++;
            }
            rs.close();
            state.close();

            query = "SELECT SUM(count) as totalCount FROM RegisterItem  INNER JOIN Storage on RegisterItem.RiNo = Storage.RiNo Where RegisterItem.RiNo = " + RegisterItemID;
            state = connection.createStatement();
            rs = state.executeQuery(query);
            int sz = 0;
            if (rs.next()) {
                sz = rs.getInt("totalCount");
            }
            rs.close();
            state.close();

//            ava = sz-len;

            // 0315 比賽用，上面 214行 記得比賽完開啟。
            ava = sz;
            // 0315 比賽用

            connection.close();
        } catch (Exception e) {
            System.out.println("error SQL DB");
        }
        return ava;
    }

    public HashMap<String, Object> fetchSingleData(String table, String condition, String... elements) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            String get_e = "";
            boolean b = false;
            for (String s : elements) {
                if (b) get_e += ",";
                get_e += s;
                b = true;
            }

            String query = "SELECT " + get_e + " FROM " + table + " WHERE " + condition;
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return result;
            for (String s : elements) {
                result.put(s, rs.getObject(s));
            }
            rs.close();
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void updatePBItemWithQRCode(int eNo, int QRCode) {
        String query = "UPDATE PreBorrow SET QRCodeNo= '" + QRCode + "' WHERE eNo = '" + eNo + "' AND QRCodeNo IS NULL ";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePBItemHoldCount(int eNo, int QRCode, int PbNo, int HoldCount) {
        String query = "UPDATE PreBorrow SET HoldCount= '" + HoldCount + "' WHERE eNo = '" + eNo + "' AND QRCodeNo = '" + QRCode + "' AND PbNo = '" + PbNo + "'";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateConsumbleCount(int CNo, int count) {
        String query = "UPDATE Consumables SET count= '" + count + "' WHERE CNo = '" + CNo + "'";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePBItem(int eNo, int RiNo, int cnt, int PbNo) {
        String query = "UPDATE PreBorrow SET PbCount='" + cnt + "' WHERE eNo = '" + eNo + "' AND RiNo = '" + RiNo + "' AND PbNo = '" + PbNo + "' AND QRCodeNo IS NULL";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePBItem(int eNo, int RiNo, int PbNo) {
        String query = "DELETE FROM PreBorrow WHERE eNo = '" + eNo + "' AND RiNo = '" + RiNo + "' AND PbNo = '" + PbNo + "' AND QRCodeNo IS NULL";
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(query);
            state.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lendItem(int eNo, int RiNo, int cnt) {
        ArrayList<HashMap<String, Object>> ret = this.cmdFetchData(
                "SELECT * FROM PreBorrow WHERE eNo = '" + eNo + "' AND RiNo = '" + RiNo + "' AND QRCodeNo IS NULL",
                "PbNo",
                "PbCount");
        if (ret.size() > 0) { // find one have null qrcode (un send)
            //update
            HashMap<String, Object> mp = ret.get(0);
            int pbno = (int) mp.get("PbNo");
            int pbcount = (int) mp.get("PbCount");
            pbcount = pbcount + cnt;
            String query = "UPDATE PreBorrow SET PbCount='" + pbcount + "' WHERE eNo = '" + eNo + "' AND RiNo = '" + RiNo + "' AND PbNo = '" + pbno + "' AND QRCodeNo IS NULL";
            try {
                Connection connection = DatabaseConnection.getConnection();
                Statement state = connection.createStatement();
                state.executeUpdate(query);
                state.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // insert
            try {

                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement newBorrow = connection.prepareStatement("INSERT INTO PreBorrow (PbNo,eNo,RiNo,PbCount,HoldCount) VALUES (?,?,?,?,?)");
                //System.out.println( player.getUniqueId());
                newBorrow.setNull(1, Types.INTEGER);//a_i
                newBorrow.setInt(2, eNo);
                newBorrow.setInt(3, RiNo);
                newBorrow.setInt(4, cnt);
                newBorrow.setInt(5, 0);
                newBorrow.executeUpdate();
                newBorrow.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


    public userData authenticate(String account, String password) {
        try {
            String query = "SELECT eNo,Name,RFIDNo FROM Employee WHERE account = '" + account + "'" + " AND " + " password='" + password + "'";
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addNodeDevice(int deviceID, String deviceType, int nodeID) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertSQL = connection.prepareStatement("INSERT INTO nodedevice (deviceID,deviceType,nodeID,insertTime) VALUES (?,?,?,NOW())");
            insertSQL.setInt(1, deviceID);
            insertSQL.setString(2, deviceType);
            insertSQL.setInt(3, nodeID);
            insertSQL.executeUpdate();
            insertSQL.close();
            System.out.println("insert 1 data to nodedevice, deviceID:" + deviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNodeIP(int deviceID, String deviceType, int nodeID) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertSQL = connection.prepareStatement("UPDATE nodedevice set ipAddr= ? WHERE nodeID= ?");
            insertSQL.setInt(1, deviceID);
            insertSQL.setString(2, deviceType);
            insertSQL.setInt(3, nodeID);
            insertSQL.executeUpdate();
            insertSQL.close();
            System.out.println("update 1 data to nodedevice, deviceID:" + deviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearNodeDevice() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statementSQL = connection.prepareStatement("TRUNCATE TABLE nodedevice");
            statementSQL.executeUpdate();
            statementSQL.close();
            System.out.println("Truncate table data of nodedevice");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StockResultData stocktakingExpensive(String BarcodeString) {
        try {
            String query = "SELECT " +
                    "s.name NAME, s.Count COUNT " +
                    "FROM " +
                    "storage s " +
                    "INNER JOIN " +
                    "barcode b ON s.BCNo = b.BCNo " +
                    "WHERE " +
                    "b.Info = '" + BarcodeString + "'";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            String storageName = rs.getString("NAME");
            int queryCount = rs.getInt("COUNT");
            StockResultData stockResultData = new StockResultData(storageName, queryCount);
            rs.close();
            state.close();
            connection.close();
            return stockResultData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateConsumableWeight(float ConsumableWeight, int ConsumableCabinetID) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            //0820 展覽用
            int packetCount = (int) Math.floor(ConsumableWeight / 150);
            //0820 展覽用
            PreparedStatement insertSQL = connection.prepareStatement("UPDATE consumables set totalWeight=?, count=?  WHERE cabinetID= ?");
            insertSQL.setFloat(1, ConsumableWeight);
            insertSQL.setInt(2, packetCount);
            insertSQL.setInt(3, ConsumableCabinetID);
            insertSQL.executeUpdate();
            insertSQL.close();
            connection.close();
            System.out.println("update totalWeight to consumable_weight, cabinetID:" + ConsumableCabinetID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePreborrowConsumer(int PbNumber, int getPacketCount) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertSQL = connection.prepareStatement("update preborrow set preborrow.HoldCount = ? where preborrow.PbNo =?");
            insertSQL.setInt(1, getPacketCount);
            insertSQL.setInt(2, PbNumber);
            insertSQL.executeUpdate();
            insertSQL.close();
            connection.close();
            System.out.println("update HoldCount to preborrow, PbNo:" + PbNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reduceExpensiveCount(String ExpensiveBarcode) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertSQL = connection.prepareStatement("UPDATE storage s " +
                    "INNER JOIN barcode b ON s.BCNo = b.BCNo " +
                    "SET s.Count = s.Count-1 " +
                    "WHERE b.Info = ?");
            insertSQL.setString(1, ExpensiveBarcode);
            int rowsAffected = insertSQL.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated ExpensiveCount in storage. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows were updated. Barcode may not exist.");
            }
            insertSQL.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StockResultData stocktakingConsumable(float ConsumableWeight, int ConsumableCabinetID) {
        try {
            String query = "SELECT consumableName, weightUnit FROM consumables WHERE cabinetID = '" + ConsumableCabinetID + "'";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            String consumableName = rs.getString("consumableName");
            float weightUnit = rs.getFloat("weightUnit");
            int packetCount = (int) Math.floor(ConsumableWeight / weightUnit);
            StockResultData stockResultData = new StockResultData(consumableName, packetCount);
            rs.close();
            state.close();
            connection.close();
            return stockResultData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int stocktakingConsumable2(int ConsumableCabinetID) {
        try {
            String query = "SELECT consumableName, totalWeight, weightUnit FROM consumables WHERE cabinetID = '" + ConsumableCabinetID + "'";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return 0;
            String consumableName = rs.getString("consumableName");
            float totalWeight = rs.getFloat("totalWeight");
            float weightUnit = rs.getFloat("weightUnit");
            int packetCount = (int) Math.floor(totalWeight / weightUnit);
            rs.close();
            state.close();
            connection.close();
            return packetCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int searchConsumerPreborrowNum(int ConsumableCabinetID) {
        String query = "select pb.PbNo PbNo from preborrow pb inner join registeritem r on pb.RiNo = r.RiNo inner join consumables c on r.RiNo = c.RiNo where c.cabinetID = ? order by pb.PbNo desc limit 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ConsumableCabinetID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("PbNo");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int searchConsumerPreCount(int ConsumableCabinetID) {
        String query = "select count from consumables where cabinetID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ConsumableCabinetID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public Integer selectNodeIDALimit1() {
        try {
            String query = "Select nodeID from nodedevice where deviceType='L' order by insertTime DESC limit 1";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            int nodeID = rs.getInt("nodeID");
            rs.close();
            state.close();
            connection.close();
            return nodeID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer selectNodeIDCLimit1() {
        try {
            String query = "Select nodeID from nodedevice where deviceType='C' order by insertTime DESC limit 1";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            int nodeID = rs.getInt("nodeID");
            rs.close();
            state.close();
            connection.close();
            return nodeID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer selectNodeIDELimit1() {
        try {
            String query = "Select nodeID from nodedevice where deviceType='E' order by insertTime DESC limit 1";
            Connection connection = DatabaseConnection.getConnection();
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.next()) return null;
            int nodeID = rs.getInt("nodeID");
            rs.close();
            state.close();
            connection.close();
            return nodeID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    public ArrayList<loadLendingData> loadLendingTools1() {
//        String query = "SELECT s.Name name, SUM(1) quantity, MAX(l.ReturnDate) returnDate FROM lend l INNER JOIN storage s ON l.sNo = s.sNo INNER JOIN employee e ON l.eNo = e.eNo WHERE e.eNo = 1 GROUP BY l.sNo";
//        ArrayList<loadLendingData> returnArray = new ArrayList<loadLendingData>();
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                String name = rs.getString("name");
//                int quantity = rs.getInt("quantity");
//                Date returnDate = rs.getDate("returnDate");
//                String returnStatus = (returnDate == null) ? "未歸還" : "已歸還";
//                loadLendingData loadLendingData = new loadLendingData(name, quantity, returnStatus, returnDate);
//                returnArray.add(loadLendingData);
//            }
//            return returnArray;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public ArrayList<loadLendingData> loadLendingTools() {
        String query = "SELECT s.Name name,1 quantity, l.LendDate lendDate, l.ReturnDate returnDate FROM lend l INNER JOIN storage s ON l.sNo = s.sNo INNER JOIN employee e ON l.eNo = e.eNo WHERE e.eNo = 1";
        ArrayList<loadLendingData> returnArray = new ArrayList<loadLendingData>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                Timestamp returnDate = rs.getTimestamp("returnDate");
                Timestamp lendDate = rs.getTimestamp("lendDate");
                String formattedReturnDate;
                String formattedLendDate;
                if (returnDate != null) {
                    formattedReturnDate = targetFormat.format(returnDate);
                } else {
                    formattedReturnDate = null;
                }

                if (lendDate != null) {
                    formattedLendDate = targetFormat.format(lendDate);
                } else {
                    formattedLendDate = null;
                }

                String returnStatus = (returnDate == null) ? "未歸還" : "已歸還";
                loadLendingData loadLendingData = new loadLendingData(name, quantity, formattedLendDate, formattedReturnDate, returnStatus);
                returnArray.add(loadLendingData);
            }
            return returnArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void updateReturnableStatus(boolean enable) {
        String query = enable ?
                "UPDATE employee SET returnable = 1 WHERE eNo = 1" :
                "UPDATE employee SET returnable = 0 WHERE eNo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Update employee returnable 1 row data: " + rowsAffected);
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean searchReturnableStatus() {
        String query = "SELECT returnable from employee where eNo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("returnable") != 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int searchReturnLendNum(String Barcode) {
        String query = "SELECT l.INo lendNUM from lend l inner join storage s on l.sNo = s.sNo inner join barcode b on s.BCNo = b.BCNo where b.Info=? and l.ReturnDate IS NULL ORDER BY l.LendDate limit 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, Barcode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("lendNUM");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void increaseExpensiveCount(String ExpensiveBarcode) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement insertSQL = connection.prepareStatement("UPDATE storage s " +
                    "INNER JOIN barcode b ON s.BCNo = b.BCNo " +
                    "SET s.Count = s.Count+1 " +
                    "WHERE b.Info = ?");
            insertSQL.setString(1, ExpensiveBarcode);
            int rowsAffected = insertSQL.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated ExpensiveCount in storage. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows were updated. Barcode may not exist.");
            }
            insertSQL.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SQLSafeUpdates() {
        String query = "SET SQL_SAFE_UPDATES=0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            } else {
                System.out.println("SET SQL_SAFE_UPDATES=0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLendData() {
        String query = "DELETE from lend";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            } else {
                System.out.println("delete lend table row data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLendPreBorrow() {
        String query = "DELETE from preborrow";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            } else {
                System.out.println("delete preborrow table row data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetKnifeCount5() {
        String query = "UPDATE storage s SET s.Count = 5 WHERE s.sNo = 20";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            } else {
                System.out.println("Reset knife count:5");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnLendKnife(int lendNum) {
        String query = "UPDATE lend l SET l.ReturnDate = now() WHERE l.INo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, lendNum);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No rows updated. Check if the condition is correct.");
            } else {
                System.out.println("Update returnLendNo returnDate 1 row data: " + lendNum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNewKnife(int qrCodeNo) {
        String query = "select reg.isNewKnife isNewKnife from registeritem reg left join preborrow pb on reg.RiNo =pb.RiNo where pb.QRCodeNo= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, qrCodeNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isNewKnife");
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
