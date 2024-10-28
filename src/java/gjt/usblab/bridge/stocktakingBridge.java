package gjt.usblab.bridge;

import gjt.usblab.Server.Server;
import gjt.usblab.data.StockResultData;

public class stocktakingBridge {
    public static StockResultData stocktakingExpensive(String Barcode, String UserRFID, Boolean isNewKnife) {
        if (Server.getInstance().sqlConnection.searchReturnableStatus()) {
            int lendNum = Server.getInstance().sqlConnection.searchReturnLendNum(Barcode);
            if (lendNum != 0) {
                Server.getInstance().sqlConnection.returnLendKnife(lendNum);
                Server.getInstance().sqlConnection.increaseExpensiveCount(Barcode);
            } else {
                System.out.println("NO any lend knife.");
            }
        } else {
            lendItemBridge.addItem(Barcode, UserRFID, isNewKnife);
            Server.getInstance().sqlConnection.reduceExpensiveCount(Barcode, isNewKnife);
        }
        StockResultData stockResultData = Server.getInstance().sqlConnection.stocktakingExpensive(Barcode, isNewKnife);
        return stockResultData;
    }

    public static StockResultData stocktakingConsumable(float ConsumableWeight, int ConsumableCabinetID) {
        Server.getInstance().sqlConnection.updateConsumableWeight(ConsumableWeight, ConsumableCabinetID);
        StockResultData stockResultData = Server.getInstance().sqlConnection.stocktakingConsumable(ConsumableWeight, ConsumableCabinetID);
        return stockResultData;
    }
}
