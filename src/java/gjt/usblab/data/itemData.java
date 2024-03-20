package gjt.usblab.data;

import gjt.usblab.Main.main;

public class itemData {
    public int id;
    public String name;
    public String filename;
    public int count;
    public String fullPath;
    public String fullINFO_url;
    public int holdCount;
    public String shelfNo;
    public itemData(int id,String name,String filename){
        this.id = id;
        this.name = name;
        this.filename= filename;
        this.fullPath = getFilePath();
        this.holdCount = 0;
    }
    public void setShelfNo(String num){
        this.shelfNo = num;
    }
    public void setHoldCount(int cnt){
        this.holdCount = cnt;
    }
    public void setURL(String url){
        this.fullINFO_url = url;
    }
    public void setCount(int n){
        this.count = n;
    }

    public String getFilePath(){
        return main.getInstance().getFilePath() + filename;
    }
}
