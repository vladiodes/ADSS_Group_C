package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.SaleMapper;
import DTO.SaleDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sale {
    private int saleID=-1;
    private String itemName;
    private int itemID;
    private double sellingPrice;
    private LocalDate saleDate;
    private int quantity;

    public Sale(int itemID, String itemName, double sellingPrice, LocalDate saleDate,int quantity){
        this.itemName = itemName;
        this.itemID=itemID;
        this.sellingPrice=sellingPrice;
        this.saleDate=saleDate;
        this.quantity=quantity;
    }
    public Sale(SaleDTO dto){
        this.itemName = dto.itemName;
        this.itemID=dto.itemID;
        this.sellingPrice=dto.sellingPrice;
        this.saleDate=dto.saleDate;
        this.quantity=dto.quantity;
        saleID= dto.id;
    }

    public void setItemID(int itemID) {
        if(this.itemID==-1)
            this.itemID=itemID;
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

