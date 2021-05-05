package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.CategoryMapper;
import BusinessLayer.Mappers.ItemsMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockController {
    // -- fields
    private static StockController stockControllerInstance = null;
    private CategoryMapper mapper;

    // -- constructor
    private StockController()
    {
        mapper=CategoryMapper.getInstance();
    }

    public static StockController getInstance(){
        if(stockControllerInstance == null)
            stockControllerInstance = new StockController();
        return stockControllerInstance;
    }


    // -- public methods

    public Item addItem(int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, int categoryID, double buyingPrice,double sellingPrice) {
        if (categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (!isAvailableLocation(location))
            throw new IllegalArgumentException("location is already taken");
        Item toReturn = CategoryMapper.getInstance().getCategory(categoryID).addItem(location,name,producer,storageAmount,shelfAmount,minAmount,expDate,buyingPrice,sellingPrice);
        return toReturn;
    }

    public void updateItem(int itemID,String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double buyingPrice,double sellingPrice){

        this.getItemByID(itemID).updateItem(name,location,producer,storageAmount,shelfAmount,minAmount,expDate,buyingPrice,sellingPrice);
    }

    private Category findCategory(int categoryID){
        return mapper.getCategory(categoryID);
    }

    public void updateCategory(int categoryID,String categoryName){
        Category cat=findCategory(categoryID);
        if(cat==null)
            throw new IllegalArgumentException("Invalid category ID");
        cat.updateCategory(categoryName);
    }
    public void addCategoryDiscount(int categoryID,double discount){

        if (categoryID<0 || findCategory(categoryID)==null)
            throw new IllegalArgumentException("invalid category id");
        if (discount<0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        findCategory(categoryID).addDiscount(discount);
    }

    public void addItemDiscount(int itemID,double discount){

        if (itemID<0)
            throw new IllegalArgumentException("invalid Item id");
        if (discount<0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        findItem(itemID).addDiscount(discount);
    }

    public Category addCategory(String name,int fatherID){
        // checks - father id = 0 -> no father category
        // check that the father ID exists
        if (fatherID<0)
            throw new IllegalArgumentException("invalid father category");
        Category FatherCategory = null;
        if(fatherID>0){
            FatherCategory=findCategory(fatherID);
            if(FatherCategory==null)
                throw new IllegalArgumentException("No such father category");
        }
        Category toAdd = new Category(name,FatherCategory);
        if (toAdd.getFatherCategory()!=null)
            toAdd.getFatherCategory().addSubCategory(toAdd);
        return toAdd;
    }

    public Item getItemByLocation(int location){
        return findItemByLocation(location);
    }
    public Item getItemById(int itemID){
        return this.findItem(itemID);
    }

    public void changeAlertTime(int itemID,int daysAmount){
        this.findItem(itemID).setAlertTime(daysAmount);
        //@todo:add alert time to db
    }


    // -- private methods
    private Item findItem(int itemID) {
        for (Category cat : mapper.getAllCategories()) {
            if (cat.getItems().containsKey(itemID))
                return cat.getItems().get(itemID);
        }
        throw new IllegalArgumentException("there is no item with this item ID");
    }
    private Item findItemByLocation(int location){
        Item i=ItemsMapper.getInstance().getItemByLocation(location);
        if(i==null)
            throw new IllegalArgumentException("there is no item in this location");
        return i;

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
        return ItemsMapper.getInstance().getItemByLocation(location)==null;
        // check in all items that the location is free
    }

    public ArrayList<Category> getAllCategories() {
        return mapper.getAllCategories();
    }

    public ArrayList<Category> getCategories(ArrayList<Integer> categoriesList) {
        ArrayList<Category> toReturn = new ArrayList<>();
        for (Category cat: mapper.getAllCategories()) {
            if (categoriesList.contains(cat.getID()))
                toReturn.add(cat);
        }
        return toReturn;
    }

    public void deleteItem(int itemID) {
        boolean deleted = false;
        for(Category cat : getAllCategories())
        {
            if(cat.containsItem(itemID)) {
                cat.deleteItem(itemID);
                deleted = true;
            }
        }
        if(!deleted)
            throw new IllegalArgumentException("Item ID does not exist");

        //@TODO: check if there's an expected order that arrives with the given id
        //@TODO: a deleted item from the store is represented with id of -1

    }

    public void sellItem(int itemID,int quantity) {

        Item item = findItem(itemID);
        if (quantity<1)
            throw new IllegalArgumentException("invalid quantity");
        if(item.getAvailableAmount()<quantity)
            throw new IllegalArgumentException("No Available items for sale");
        //@TODO sale item should return true/false upon missing items
        item.SaleItem(quantity);
        //@TODO open a new order for the low quantity item
    }


}
