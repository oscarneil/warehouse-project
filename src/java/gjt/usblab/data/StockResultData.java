package gjt.usblab.data;

public class StockResultData {
    private String productName;
    private int stockCount;
    private float stockWeight;

    public StockResultData(String productName, int stockCount) {
        this.productName = productName;
        this.stockCount = stockCount;
    }

    public StockResultData(String productName, float stockWeight) {
        this.productName = productName;
        this.stockWeight = stockWeight;
    }

    public StockResultData() {

    }

    public String getProductName() {
        return productName;
    }

    public int getStockCount() {
        return stockCount;
    }

    public float getStockWeight() {
        return stockWeight;
    }

    public void setStockWeight(float stockWeight) {
        this.stockWeight = stockWeight;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
