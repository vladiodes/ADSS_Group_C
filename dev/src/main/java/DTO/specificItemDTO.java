package DTO;

import BusinessLayer.InventoryModule.SpecificItem;

import java.time.LocalDate;

public class specificItemDTO {
    public int id;
    public LocalDate expDate;
    public int storageAmount;
    public int shelfAmount;
    public int generalID;

    public specificItemDTO(int id,LocalDate expDate,int storageAmount,int shelfAmount,int generalID) {
        this.id = id;
        this.expDate = expDate;
        this.storageAmount = storageAmount;
        this.shelfAmount = shelfAmount;
        this.generalID=generalID;
    }
    public specificItemDTO(SpecificItem item,int generalID) {
        this.id = item.getId();
        this.expDate = item.getExpDate();
        this.storageAmount = item.getStorageAmount();
        this.shelfAmount = item.getShelfAmount();
        this.generalID=generalID;
    }

}
