package BusinessLayer.InventoryModule;


import BusinessLayer.Mappers.ItemsMapper;
import DTO.specificItemDTO;

import java.time.LocalDate;

public class SpecificItem {
    private int id=-1;
    private int shelfAmount;
    private int storageAmount;
    private LocalDate expDate;
    private int generalItemID; //for db completeness


    public SpecificItem(int storageAmount, int shelfAmount, LocalDate expDate,int generalItemID)
    {
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.expDate=expDate;
        this.generalItemID=generalItemID;
    }
    public SpecificItem(specificItemDTO dto){
        id=dto.id;
        shelfAmount=dto.shelfAmount;
        storageAmount=dto.storageAmount;
        expDate=dto.expDate;
        this.generalItemID=dto.generalID;
    }

    public int getAvailableAmount() {
        return shelfAmount+storageAmount;
    }


    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
        ItemsMapper.getInstance().updateSpecificItem(this);
    }

    public int getShelfAmount() {
        return shelfAmount;
    }

    public void setShelfAmount(int shelfAmount) {
        this.shelfAmount = shelfAmount;
        ItemsMapper.getInstance().updateSpecificItem(this);
    }

    public LocalDate getExpDate() {
        return expDate;
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

    public void setId(int id){
        if(this.id==-1)
            this.id=id;
    }

    public int getId(){
        return id;
    }

    public int getGeneralItemID() {
        return generalItemID;
    }
}
