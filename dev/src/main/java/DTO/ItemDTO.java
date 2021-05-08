package DTO;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.InventoryModule.SpecificItem;

import java.util.ArrayList;
import java.util.List;

public class ItemDTO {
    private int id;
    private String itemName;
    private int availableAmount;
    private int alertTime;
    private int categoryID;
    private int location;
    private String producer;
    private int minAmount;
    private double sellingPrice;
    private double buyingPrice;
    private List<specificItemDTO> specificItemDTOList;

    public ItemDTO(int id, String name, int location, String producer,int minAmount, double sellingPrice, int categoryID, int alertTime){
        this.id=id;
        this.producer=producer;
        this.location=location;
        itemName=name;
        this.minAmount=minAmount;
        this.sellingPrice=sellingPrice;
        this.categoryID=categoryID;
        this.alertTime=alertTime;
    }
    public ItemDTO(Item i){
        this.id=i.getId();
        this.itemName = i.getName();
        this.location=i.getLocation();
        this.producer=i.getProducer();
        this.setAvailableAmount(i.getAmount());
        this.setMinAmount(i.getMinAmount());
        this.setAlertTime(i.getAlertTime());
        sellingPrice=i.getSellingPrice();
        categoryID=i.getCategoryID();
        buyingPrice=i.getBuyingPrice();
        specificItemDTOList=new ArrayList<>();
        for(SpecificItem specificItem:i.getSpecificItems()){
            specificItemDTOList.add(new specificItemDTO(specificItem.getId(),specificItem.getExpDate(), specificItem.getStorageAmount(), specificItem.getShelfAmount(),i.getId()));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.id + "\n");
        builder.append("Item Name: " + this.itemName + "\n");
        builder.append("Item Location: "+ this.location + "\n");
        builder.append("Item Producer: "+ this.producer + "\n");
        builder.append("Item Selling price: " + sellingPrice + "\n");
        builder.append("Item Buying price: " + buyingPrice + "\n");
        builder.append("Item Available Amount: "+ this.availableAmount + "\n");
        builder.append("Item Minimum Amount: "+ this.minAmount + "\n");
        builder.append("Item Alert Time: "+ this.alertTime);
        return builder.toString();
    }

    public int getLocation() {
        return location;
    }

    public String getProducer() {
        return producer;
    }

    public void setSpecificItemDTOList(List<specificItemDTO> specificItemDTOList) {
        this.specificItemDTOList = specificItemDTOList;
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

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }

    public int getID() {
        return this.id;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getAlertTime() {
        return this.alertTime;
    }

    public String getName() {
        return this.itemName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public List<specificItemDTO> getSpecificItemDTOList() {
        return specificItemDTOList;
    }
}