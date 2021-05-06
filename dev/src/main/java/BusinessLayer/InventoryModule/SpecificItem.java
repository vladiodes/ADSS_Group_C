package BusinessLayer.InventoryModule;

import BusinessLayer.SuppliersModule.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpecificItem {
    private int id;
    private String producer;
    private int storageAmount;
    private int shelfAmount;
    private int location;
    private LocalDate expDate;


    public SpecificItem(int id,int location,  int storageAmount, int shelfAmount, LocalDate expDate, String producer)
    {
        this.producer=producer;

        this.id = id;
        this.setStorageAmount(storageAmount);
        this.setShelfAmount(shelfAmount);
        this.setLocation(location);
        this.setExpDate(expDate);

    }

    public int getAvailableAmount() {
        return shelfAmount+storageAmount;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
    public boolean isFaulty() {
        if (this.expDate.compareTo(LocalDate.now())<=0)
            return true;
        return false;
    }

    public boolean isExp(int alertTime) {
        if (this.expDate.minusDays(alertTime).compareTo(LocalDate.now())<=0)
            return true;
        return false;
    }

}
