package BusinessLayer;

import DTO.ItemDTO;

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
    private LocalDateTime expDate;
    private int alertTime;
    private double buyingPrice;

    // -- constructor

    public Item(int id,String name, int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice){
        this.id=id;
        this.name=name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=availableAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
        this.alertTime=2;
        this.buyingPrice=buyingPrice;
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

    public LocalDateTime getExpDate() {
        return expDate;
    }

    public String getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateItem(String name, int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice)
    {
        if(name == null || name == "")
            throw new IllegalArgumentException("invalid item name");
        if(location < 0)
            throw new IllegalArgumentException("invalid item location");
        if(producer == null || producer == "")
            throw new IllegalArgumentException("invalid producer name");
        if (availableAmount<0)
            throw new IllegalArgumentException("invalid available amount");
        if (storageAmount<0)
            throw new IllegalArgumentException("invalid storage amount");
        if (shelfAmount<0)
            throw new IllegalArgumentException("invalid shelf amount");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (expDate.compareTo(LocalDateTime.now())<0)
            throw new IllegalArgumentException("invalid exp date");
        if (buyingPrice<0)
            throw new IllegalArgumentException("invalid buying price");

        this.name = name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=availableAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
    }

    public void addDiscount(double discount) {
        this.buyingPrice=buyingPrice-buyingPrice*discount/100;
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
         if (this.expDate.compareTo(LocalDateTime.now())>0)
             return true;
         return false;
    }

    public boolean isExp() {
        if (this.expDate.minusDays(alertTime).compareTo(LocalDateTime.now())>0)
            return true;
        return false;
    }

    public boolean isMinAmount() {
        return this.minAmount>=this.availableAmount;
    }
}

