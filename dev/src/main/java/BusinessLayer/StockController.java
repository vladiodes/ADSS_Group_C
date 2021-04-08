package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StockController {
    // -- fields
    private static StockController stockControllerInstance = null;
    private int categoryID;
    private int itemID; // by father category
    private HashMap<Integer,Category> categories;

    // -- constructor
    private StockController()
    {
        this.categoryID =0;
        this.itemID=0;
        this.categories = new HashMap<>();
    }

    public static StockController getInstance(){
        if(stockControllerInstance == null)
            stockControllerInstance = new StockController();
        return stockControllerInstance;
    }


    // -- public methods

    public Item addItem(int location,String name, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,int categoryID,double buyingPrice) {


        if (categoryID>=this.categoryID | categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (!isAvailableLocation(location))
            throw new IllegalArgumentException("location is already token");
        Item toReturn = this.categories.get(categoryID).addItem(location,name,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,itemID,buyingPrice);
        itemID++;
        return toReturn;
    }

    public void updateItem(int itemID,String name, int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice){

        this.getItemByID(itemID).updateItem(name,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
    }

    public void updateCategory(int categoryID,String categoryName){
        this.categories.get(categoryID).updateCategory(categoryName);
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
        Category FatherCategory;
        if (fatherID<0 | fatherID>this.categoryID)
            throw new IllegalArgumentException("invalid father category");
        if (fatherID==0)
            FatherCategory = null;
        else
            FatherCategory = this.categories.get(fatherID);
        Category toAdd = new Category(name,this.categoryID,FatherCategory);
        this.categories.put(this.categoryID,toAdd);
        FatherCategory.addSubCategory(toAdd);
        this.categoryID++;
        return toAdd;
    }

    public Item getItemByLocation(int location){
        if (isAvailableLocation(location))
            throw new IllegalArgumentException("there is no item in this location");
        return this.findItemByLocation(location);
    }
    public Item getItemById(int itemID){
        return this.findItem(itemID);
    }

    public void changeAlertTime(int itemID,int daysAmount){
        this.findItem(itemID).setAlertTime(daysAmount);
    }


    // -- private methods
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
    private Item getItemByID(int id) {
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

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> toReturn = new ArrayList<>();
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            toReturn.add(value);
        }
        return toReturn;
    }

    public ArrayList<Category> getCategories(ArrayList<Integer> categoriesList) {
        ArrayList<Category> toReturn = new ArrayList<>();
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            if (categoriesList.contains(entry.getKey()))
                toReturn.add(value);
        }
        return toReturn;
    }
}
