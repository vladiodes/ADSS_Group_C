package BusinessLayer;

import DTO.ItemDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Item {
    // -- fields
    private int id;
    private String name;
    private int location;
    private String producer;
    private int availableAmount;
    private int storageAmount;
    private int shelfAmount;
    private int minAmount;
    private LocalDate expDate;
    private int alertTime;
    private double buyingPrice;
    private double sellingPrice;

    // -- constructor

    public Item(int id, String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, double buyingPrice,double sellingPrice){
        this.id=id;
        this.name=name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=storageAmount+shelfAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
        this.alertTime=2;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
    }

    @Override
    public String toString() {
        return "item{" +
                "id=" + id +
                ", location=" + location +
                ", producer='" + producer + '\'' +
                ", availableAmount=" + availableAmount +
                ", storageAmount=" + storageAmount +
                ", shelfAmount=" + shelfAmount +
                ", minAmount=" + minAmount +
                ", expDate=" + expDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public int getLocation() {
        return location;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getShelfAmount() {
        return shelfAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public String getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateItem(String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double buyingPrice,double sellingPrice)
    {
        if(name == null || name == "")
            throw new IllegalArgumentException("invalid item name");
        if(location < 0)
            throw new IllegalArgumentException("invalid item location");
        if(producer == null || producer == "")
            throw new IllegalArgumentException("invalid producer name");
        if (storageAmount<0)
            throw new IllegalArgumentException("invalid storage amount");
        if (shelfAmount<0)
            throw new IllegalArgumentException("invalid shelf amount");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (expDate.compareTo(LocalDate.now())<0)
            throw new IllegalArgumentException("invalid exp date");
        if (buyingPrice<0)
            throw new IllegalArgumentException("invalid buying price");
        if (sellingPrice<0)
            throw new IllegalArgumentException("invalid selling price");

        this.name = name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=shelfAmount+storageAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
    }

    public void addDiscount(double discount) {
        this.sellingPrice=sellingPrice-sellingPrice*discount/100;
    }

    public int getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(int alertTime) {
        if(alertTime < 0)
            throw new IllegalArgumentException("invalid alert time");
        this.alertTime = alertTime;
    }

    public boolean isFaulty() {
         if (this.expDate.compareTo(LocalDate.now())<=0)
             return true;
         return false;
    }

    public boolean isExp() {
        if (this.expDate.minusDays(alertTime).compareTo(LocalDate.now())<=0)
            return true;
        return false;
    }

    public boolean isMinAmount() {
        return this.minAmount>=this.availableAmount;
    }

    public void SaleItem(int quantity) {
        if(shelfAmount >= quantity)
            this.shelfAmount=shelfAmount-quantity;
        else if(shelfAmount>0) {
                quantity = quantity - shelfAmount;
                shelfAmount = 0;
                storageAmount = storageAmount - quantity;
        }
        else
                storageAmount=storageAmount-quantity;
        this.availableAmount=this.shelfAmount+this.storageAmount;


    }
}

