package BusinessLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private int saleID;
    private String itemName;
    private int itemID;
    private double buyingPrice;
    private double sellingPrice;
    private LocalDate saleDate;
    private int quantity;

    public Sale(int saleID, int itemID, String itemName, double buyingPrice, double sellingPrice, LocalDate saleDate,int quantity){
        this.saleID=saleID;
        this.itemName = itemName;
        this.itemID=itemID;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
        this.saleDate=saleDate;
        this.quantity=quantity;
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

    public LocalDate getSaleDate() {
        return saleDate;
    }
    public String getItemName(){
        return this.itemName;
    }

    public int getSaleID() {
        return saleID;
    }

    public int getQuantity() {
        return this.quantity;
    }
}

