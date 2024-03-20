package gjt.usblab.data;


public class userData {
    public int eNo;
    public String name;
    public String RFID;
    public int PreBorrowQRCode;
    public String PreBorrowQRCodeURL;
    public boolean inPreBorrow;
    public int PreReturnQRCode;
    public String PreReturnQRCodeURL;
    public boolean inPreReturn;
    public userData(int eNo,String Name,String RFID){
        this.eNo = eNo;
        this.name = Name;
        this.RFID = RFID;
    } 
    public int PreBorrowCode(){
        return PreBorrowQRCode;
    }
    public int PreReturnCode(){
        return PreReturnQRCode;
    }

    public void setPreBorrowCode(int code){
        this.PreBorrowQRCode = code;
        this.inPreBorrow = true;
    }

    public void setPreBorrowCodeURL(String code){
        this.PreBorrowQRCodeURL = code;
    }

    public void setPreReturnCode(int code){
        this.PreReturnQRCode = code;
        this.inPreReturn = true;
    }
    public void setPreReturnCodeURL(String code){
        this.PreReturnQRCodeURL = code;
    }
    
    public void clearPreBorrowCode(){
        this.PreBorrowQRCode = -1;
        this.inPreBorrow = false;
    }

    public void clearPreReturnCode(){
        this.PreReturnQRCode = -1;
        this.inPreReturn = false;
    }
}
