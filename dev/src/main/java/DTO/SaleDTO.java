package DTO;

import BusinessLayer.InventoryModule.Sale;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SaleDTO {
    public int id;
    public int itemID;
    public String itemName;
    public double sellingPrice;
    public LocalDate saleDate;
    public int quantity;

    public SaleDTO(int id, int itemID, String itemName, double sellingPrice, LocalDate saleDate,int quantity){
        this.id=id;
        this.itemID=itemID;
        this.itemName=itemName;
        this.sellingPrice=sellingPrice;
        this.saleDate=saleDate;
        this.quantity=quantity;
    }
    public SaleDTO(Sale s){
        this.id=s.getSaleID();
        this.itemID=s.getItemID();
        this.itemName = s.getItemName();
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
        builder.append("Selling Price: "+ this.sellingPrice + ", ");
        builder.append("Sale Date: "+ saleDate.format(DateTimeFormatter.ISO_LOCAL_DATE)+"\n");
        return builder.toString();
    }
}
