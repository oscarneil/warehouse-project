package gjt.usblab.data;

import java.sql.Timestamp;
import java.util.Date;

public class loadLendingData {
    private String name;
    private int quantity;
    private String returnStatus;
    private String lendDate;
    private String returnDate;

    public loadLendingData(String name, int quantity, String lendDate, String returnDate, String returnStatus) {
        this.name = name;
        this.quantity = quantity;
        this.returnStatus = returnStatus;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public loadLendingData() {
    }

}
