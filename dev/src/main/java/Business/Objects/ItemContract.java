package Business.Objects;

import java.util.HashMap;

public class ItemContract {
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
}
