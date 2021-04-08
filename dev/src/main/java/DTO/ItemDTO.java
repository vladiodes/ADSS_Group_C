package DTO;

import BusinessLayer.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemDTO {
    private int id;
    private String itemName;
    private int location;
    private String producer;
    private int availableAmount;
    private int storageAmount;
    private int shelfAmount;
    private int minAmount;
    private LocalDateTime expDate;
    private int alertTime;

    public ItemDTO(Item i){
        this.id=i.getId();
        this.itemName = i.getName();
        this.setLocation(i.getLocation());
        this.setProducer(i.getProducer());
        this.setAvailableAmount(i.getAvailableAmount());
        this.setStorageAmount(i.getStorageAmount());
        this.setShelfAmount(i.getShelfAmount());
        this.setMinAmount(i.getMinAmount());
        this.setExpDate(i.getExpDate());
        this.setAlertTime(i.getAlertTime());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.id + "\n");
        builder.append("Item Name: " + this.itemName + "\n");
        builder.append("Item Location: "+ this.location + "\n");
        builder.append("Item Producer: "+ this.producer + "\n");
        builder.append("Item Available Amount: "+ this.availableAmount + "\n");
        builder.append("Item Storage Amount: "+ this.storageAmount + "\n");
        builder.append("Item Shelf Amount: "+ this.shelfAmount + "\n");
        builder.append("Item Minimum Amount: "+ this.minAmount + "\n");
        builder.append("Item Expiration Date: "+ this.expDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Item Alert Time: "+ this.alertTime);
        return builder.toString();
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }

    public int getShelfAmount() {
        return shelfAmount;
    }

    public void setShelfAmount(int shelfAmount) {
        this.shelfAmount = shelfAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public LocalDateTime getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDateTime expDate) {
        this.expDate = expDate;
    }

    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }

    public int getID() {
        return this.id;
    }

    public int getAlertTime() {
        return this.alertTime;
    }

    public String getName() {
        return this.itemName;
    }
}
