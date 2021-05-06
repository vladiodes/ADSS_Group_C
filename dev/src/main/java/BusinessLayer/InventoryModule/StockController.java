package BusinessLayer.InventoryModule;

import BusinessLayer.SuppliersModule.Contract;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.SuppliersModule.ProductInOrder;
import BusinessLayer.SuppliersModule.Supplier;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StockController {
    // -- fields
    private static StockController stockControllerInstance = null;
    private int categoryID;
    private int itemID; // by father category
    private HashMap<Integer,Category> categories;

    // -- constructor
    private StockController()
    {
        this.categoryID =1;
        this.itemID=1;
        this.categories = new HashMap<>();
    }

    public static StockController getInstance(){
        if(stockControllerInstance == null)
            stockControllerInstance = new StockController();
        return stockControllerInstance;
    }


    // -- public methods
    //@TODO : add specific item option (FINISHED)
    public boolean addSpecificItem(int generalItemID,int location,  int storageAmount, int shelfAmount, LocalDate expDate, String producer)
    {
        Item item = findItem(generalItemID);
        item.addSpecificItem(location,storageAmount,shelfAmount,expDate,producer);
        return true;
    }
    public Item addItem(int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, int categoryID, double buyingPrice,double sellingPrice) {


        if (categoryID>=this.categoryID | categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (!isAvailableLocation(location))
            throw new IllegalArgumentException("location is already token");
        Item toReturn = this.categories.get(categoryID).addItem(location,name,producer,storageAmount,shelfAmount,minAmount,expDate,itemID,buyingPrice,sellingPrice);
        itemID++;
        return toReturn;
    }

    public void updateItem(int itemID,String name,   int minAmount, double buyingPrice,double sellingPrice){

        this.getItemByID(itemID).updateItem(name,minAmount,buyingPrice,sellingPrice);
    }

    public void updateCategory(int categoryID,String categoryName){
        if(this.categories.containsKey(categoryID))
            this.categories.get(categoryID).updateCategory(categoryName);
        else
            throw new IllegalArgumentException("Invalid category ID");
    }
    public void addCategoryDiscount(int categoryID,double discount){

        if (categoryID>=this.categoryID | categoryID<0)
            throw new IllegalArgumentException("invalid category id");
        if (discount<0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        this.categories.get(categoryID).addDiscount(discount);
    }

    public void addItemDiscount(int itemID,double discount){

        if (itemID>=this.itemID | itemID<0)
            throw new IllegalArgumentException("invalid Item id");
        if (discount<0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        findItem(itemID).addDiscount(discount);
    }

    public Category addCategory(String name,int fatherID){
        // checks - father id = 0 -> no father category
        // check that the father ID exists
        Category FatherCategory;
        if(this.categoryID == 1 && fatherID == 1)
        {
            throw new IllegalArgumentException("No Categories yet , Cannot add father category 1");
        }
        if (fatherID<0 | fatherID>this.categoryID)
            throw new IllegalArgumentException("invalid father category");
        if (fatherID==0)
            FatherCategory = null;
        else
            FatherCategory = this.categories.get(fatherID);
        Category toAdd = new Category(name,this.categoryID,FatherCategory);
        this.categories.put(this.categoryID,toAdd);
        if (toAdd.getFatherCategory()!=null)
            toAdd.getFatherCategory().addSubCategory(toAdd);
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
                List<SpecificItem> items = entry1.getValue().getSpecificItems();
                for(SpecificItem item : items)
                {
                    if (item.getLocation()==location)
                        return entry1.getValue();
                }

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
                List<SpecificItem> items = entry1.getValue().getSpecificItems();
                for(SpecificItem item : items)
                {
                    if (item.getLocation()==location)
                        return false;
                }
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

    public void deleteItem(int itemID) {
        boolean deleted = false;
        List<Supplier> allSuppliers = SuppliersController.getInstance().getAllSuppliers();
        // check if the item exists in an order and if so remove it from the order
        for(Supplier s : allSuppliers)
        {
            List<Order> allOrdersBySupplier = s.getOrders();
            for(Order o : allOrdersBySupplier)
            {
                Set<ProductInOrder> allProductsInOrder = o.getProductsInOrder();
                for(ProductInOrder p : allProductsInOrder)
                {
                    Contract c = p.getContract();
                    if(c.getProduct().getId()== itemID)
                    {
                        o.removeProduct(c,s.getDiscountsByPrice());
                    }
                }
            }
        }
        for(Category cat : categories.values())
        {
            if(cat.containsItem(itemID)) {
                cat.deleteItem(itemID);
                this.findItem(itemID).setID(-1);
                deleted = true;
            }
        }
        if(!deleted)
            throw new IllegalArgumentException("Item ID does not exist");

        //@TODO: check if there's an expected order that arrives with the given id (FINISHED)


        //@TODO: a deleted item from the store is represented with id of -1 (FINISHED)


    }
    public void removeFaultyItems()
    {
        for (Map.Entry<Integer, Category> entry : this.categories.entrySet()) {
            Category value = entry.getValue();
            for (Map.Entry<Integer, Item> entry1 : value.getItems().entrySet()) {
                Item item = entry1.getValue();
                item.removeFaultyItems();
                if(item.getAmount() < item.getMinAmount())
                {
                    addOrder(item);
                }
            }
        }
    }

    public void sellItem(int itemID,int quantity) {
        boolean needToOrder = false;
        Item item = findItem(itemID);
        if (quantity<1)
            throw new IllegalArgumentException("invalid quantity");
        if(item.getAmount()<quantity)
            throw new IllegalArgumentException("No Available items for sale");
        needToOrder = item.SaleItem(quantity);
        //@TODO : open a new order for the low quantity item (FINISHED)
        //@TODO : need to get the SupplierFacade and supplierID (FINISHED)
        if(needToOrder)
        {
            addOrder(item);
        }
    }
    private void addOrder(Item item)
    {
        if(item.getId() != -1) // if the item isn't deleted
        {
            Pair<Integer,Integer> supIDAndCatID = item.getCheapestSupplier();
            SuppliersController controller = SuppliersController.getInstance();
            int newOrderID = controller.openOrder(supIDAndCatID.getKey(), LocalDateTime.now(),false);
            controller.addItemToOrder(supIDAndCatID.getKey(),newOrderID,item.getMinAmount()-item.getAmount(),supIDAndCatID.getValue());
        }
    }
    public void clear() {
        this.categories=new HashMap<>();
        this.categoryID=1;
        this.itemID=1;

    }
}
