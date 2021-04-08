package BusinessLayer;

import java.time.LocalDateTime;

public class Sale {
    private int saleID;
    private String itemName;
    private int itemID;
    private double buyingPrice;
    private double sellingPrice;
    private LocalDateTime saleDate;

    public Sale(int saleID,int itemID,String itemName,double buyingPrice,double sellingPrice,LocalDateTime saleDate){
        this.saleID=saleID;
        this.itemName = itemName;
        this.itemID=itemID;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
        this.saleDate=saleDate;
    }

    @Override
    public String toString() {
        return "sale{" +
                "itemID=" + itemID +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", saleDate=" + saleDate +
                '}';
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getItemID() {
        return itemID;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    public String getItemName(){
        return this.itemName;
    }

    public int getSaleID() {
        return saleID;
    }
}

