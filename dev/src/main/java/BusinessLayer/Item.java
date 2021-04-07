package BusinessLayer;

import DTO.ItemDTO;

import java.time.LocalDateTime;
import java.util.Date;

public class Item {
    private int id;
    private int location;
    private String producer;
    private int availableAmount;
    private int storageAmount;
    private int shelfAmount;
    private int minAmount;
    private LocalDateTime expDate;
    private int alertTime;
    private double buyingPrice;

    public Item(int id, int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice){
        this.id=id;
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

    public void updateItem(ItemDTO item)
    {

        if (availableAmount<1)
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

        this.location=item.getLocation();
        this.producer=item.getProducer();
        this.availableAmount=item.getAvailableAmount();
        this.storageAmount=item.getStorageAmount();
        this.shelfAmount=item.getShelfAmount();
        this.minAmount=item.getMinAmount();
        this.expDate=item.getExpDate();
        this.alertTime=item.getAlertTime();
    }

    public void addDiscount(double discount) {
        this.buyingPrice=buyingPrice-buyingPrice*discount/100;
    }

    public int getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }
}

