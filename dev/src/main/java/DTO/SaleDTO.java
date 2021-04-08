package DTO;

import BusinessLayer.Sale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SaleDTO {
    private int itemID;
    private String itemName;
    private double buyingPrice;
    private double sellingPrice;
    private LocalDateTime saleDate;

    public SaleDTO(Sale s){
        this.itemID=s.getItemID();
        this.itemName = s.getItemName();
        this.buyingPrice=s.getBuyingPrice();
        this.sellingPrice=s.getSellingPrice();
        this.saleDate=s.getSaleDate();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.itemID + "\n");
        builder.append("Item Name: "+ this.itemName + "\n");
        builder.append("Buying Price: "+ this.buyingPrice + "\n");
        builder.append("Selling Price: "+ this.sellingPrice + "\n");
        builder.append("Sale Date: "+ saleDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return builder.toString();
    }
}
