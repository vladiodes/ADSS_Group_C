package Business.Objects;

import Data.DTO.ItemContractDTO;
import java.util.HashMap;

public class ItemContract implements persistentObject<ItemContractDTO> {
    private int ID;
    private Site destination;
    private HashMap<String, Integer> items;
    private Boolean passed;

    public ItemContract(int ID, Site destination, HashMap<String, Integer> items, Boolean passed) {
        setID(ID);
        setDestination(destination);
        setItems(items);
        setPassed(passed);
    }
/*
    public ItemContract(ItemContractDTO dto){
        setID(dto.ID);
        setDestination(dto.destination); //////////////// TODO ///////////////////////////////////
        setItems(dto.items);
        setPassed(dto.passed);
    }
*/
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Site getDestination() {
        return destination;
    }

    public void setDestination(Site destination) {
        this.destination = destination;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    @Override
    public String toString() {
        return "ItemContract{" +
                "ID=" + ID +
                ", destination=" + destination +
                ", items=" + items +
                ", passed=" + passed +
                '}';
    }

    @Override
    public ItemContractDTO toDTO() {
        return new ItemContractDTO(getID(),getDestination().getAddress(),getItems(),getPassed());
    }
}
