package DTO;

import BusinessLayer.Sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SaleDTO {
    private int itemID;
    private String itemName;
    private double buyingPrice;
    private double sellingPrice;
    private LocalDate saleDate;
    private int quantity;

    public SaleDTO(Sale s){
        this.itemID=s.getItemID();
        this.itemName = s.getItemName();
        this.buyingPrice=s.getBuyingPrice();
        this.sellingPrice=s.getSellingPrice();
        this.saleDate=s.getSaleDate();
        this.quantity=s.getQuantity();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.itemID + " ,  ");
        builder.append("Item Name: "+ this.itemName + ", ");
        builder.append("Quantity : "+ this.quantity + ", ");
        builder.append("Buying Price: "+ this.buyingPrice + ", ");
        builder.append("Selling Price: "+ this.sellingPrice + ", ");
        builder.append("Sale Date: "+ saleDate.format(DateTimeFormatter.ISO_LOCAL_DATE)+"\n");
        return builder.toString();
    }
}
