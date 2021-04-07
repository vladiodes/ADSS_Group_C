package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StockController {
    private int categoryID;
    private int itemID; // by father category
    private HashMap<Integer,Category> categories;

    // fields and constructor
    public StockController()
    {
        this.categoryID =0;
        this.itemID=0;
        this.categories = new HashMap<>();
    }



    public Item addItem(int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,int categoryID,double buyingPrice) {


        if (categoryID>=this.categoryID | categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (!isAvailableLocation(location))
            throw new IllegalArgumentException("location is already token");
        Item toReturn = this.categories.get(categoryID).addItem(location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,itemID,buyingPrice);
        itemID++;
        return toReturn;
    }

    public void updateItem(ItemDTO itemDTO){

        this.getItemByID(itemDTO.getID()).updateItem(itemDTO);
    }

    public void updateCategory(CategoryDTO categoryDTO){
        this.categories.get(categoryDTO.getID()).updateCategory(categoryDTO);
    }

    public void addCategoryDiscount(int categoryID,double discount){

        if (categoryID>=this.categoryID | categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (discount<0)
            throw new IllegalArgumentException("invalid discount amount");
        this.categories.get(categoryID).addDiscount(discount);
    }

    public void addItemDiscount(int itemID,double discount){

        if (itemID>=this.itemID | itemID<0)
            throw new IllegalArgumentException("invalid category id");
        if (discount<0)
            throw new IllegalArgumentException("invalid discount amount");
        findItem(itemID).addDiscount(discount);
    }

    public Category addCategory(String name,int fatherID){
        // checks - father id = 0 -> no father category
        // check that the father ID exists
        if (fatherID<0 | fatherID>this.categoryID)
            throw new IllegalArgumentException("invalid father category");
        Category toAdd = new Category(name,this.categoryID);
        this.categories.put(this.categoryID,toAdd);
        this.categoryID++;
        return toAdd;
    }

    public Item getItemByLocation(int location){
        if (isAvailableLocation(location))
            throw new IllegalArgumentException("there is no item in this location");
        return this.findItemByLocation(location);
    }

    public void changeAlertTime(int itemID,int daysAmount){
        this.findItem(itemID).setAlertTime(daysAmount);
    }




    // search the item by id in categories, return the item
// have to throw exceptions
    private Item findItem(int itemID) {
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            if (value.getItems().containsKey(itemID))
                return value.getItems().get(itemID);
        }
        throw new IllegalArgumentException("there is no item with this item ID");
    }
    private Item findItemByLocation(int location){
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            for (Map.Entry<Integer, Item> entry1 : value.getItems().entrySet()) {
                if (entry1.getValue().getLocation()==location)
                    return entry1.getValue();
            }
    }
        throw new IllegalArgumentException("there is no item in this location");
}
    private Item getItemByID(int id)
    {
        Item item = findItem(id);
        if(item == null)
            throw new IllegalArgumentException("wrong id");
        return item;
    }

    private boolean isAvailableLocation(int location){
        if (location<0)
            throw new IllegalArgumentException("invalid location");
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            for (Map.Entry<Integer, Item> entry1 : value.getItems().entrySet()) {
                if (entry1.getValue().getLocation()==location)
                    return false;
            }
        }
        return true;

        // check in all items that the location is free
    }
}
