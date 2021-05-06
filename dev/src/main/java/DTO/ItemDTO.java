package DTO;

import BusinessLayer.InventoryModule.Item;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemDTO {
    private int id;
    private String itemName;
    private int availableAmount;
    private int minAmount;
    private int alertTime;

    public ItemDTO(Item i)
    {
        this.setId(i.getId());
        this.setItemName(i.getName());
        this.setAvailableAmount(i.getAmount());
        this.setMinAmount(i.getMinAmount());
        this.setAlertTime(i.getAlertTime());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.getId() + "\n");
        builder.append("Item Name: " + this.getItemName() + "\n");
        builder.append("Item Available Amount: "+ this.getAvailableAmount()+ "\n");
        builder.append("Item Minimum Amount: "+ this.getMinAmount() + "\n");
        builder.append("Item Alert Time: "+ this.getAlertTime());
        return builder.toString();
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }



    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }


    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }

    public int getAlertTime() {
        return this.alertTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
