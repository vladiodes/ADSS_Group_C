package DTO;

import BusinessLayer.Sale;

import java.time.LocalDateTime;
import java.util.Date;

public class SaleDTO {
    private int itemID;
    private double buyingPrice;
    private double sellingPrice;
    private LocalDateTime saleDate;

    public SaleDTO(Sale s){
        this.itemID=s.getItemID();
        this.buyingPrice=s.getBuyingPrice();
        this.sellingPrice=s.getSellingPrice();
        this.saleDate=s.getSaleDate();
    }

    @Override
    public String toString() {
        return "saleDTO{" +
                "itemID=" + itemID +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", saleDate=" + saleDate +
                '}';
    }
}
