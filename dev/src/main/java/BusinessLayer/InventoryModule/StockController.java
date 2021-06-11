package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.CategoryMapper;
import BusinessLayer.Mappers.ItemsMapper;
import BusinessLayer.SuppliersModule.Contract;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.SuppliersModule.ProductInOrder;
import BusinessLayer.SuppliersModule.Supplier;
import Misc.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StockController {
    // -- fields
    private static StockController stockControllerInstance = null;
    private CategoryMapper categoryMapper;

    // -- constructor
    private StockController() {
        categoryMapper = CategoryMapper.getInstance();
    }

    public static StockController getInstance() {
        if (stockControllerInstance == null)
            stockControllerInstance = new StockController();
        return stockControllerInstance;
    }


    // -- public methods
    public boolean addSpecificItem(int generalItemID, int storageAmount, int shelfAmount, LocalDate expDate) {
        Item item = findItem(generalItemID);
        item.addSpecificItem(storageAmount, shelfAmount, expDate);
        return true;
    }

    public Item addItem(int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, int categoryID, double sellingPrice,int weight) {

        if (!isAvailableLocation(location))
            throw new IllegalArgumentException("location is already taken");
        Category c = categoryMapper.getCategory(categoryID);
        if (c == null)
            throw new IllegalArgumentException("No such category");
        Item toReturn = c.addItem(location, name, producer, storageAmount, shelfAmount, minAmount, expDate, sellingPrice,weight);
        return toReturn;
    }

    public void updateItem(int itemID, String name, int minAmount, double sellingPrice, int location, String producer) {
        Item item = findItem(itemID);
        if (findItemByLocation(location) != item)
            throw new IllegalArgumentException("Location is taken");
        item.updateItem(name, minAmount, sellingPrice, location, producer);
    }

    public void updateCategory(int categoryID, String categoryName) {
        Category category = categoryMapper.getCategory(categoryID);
        if (category != null)
            category.updateCategory(categoryName);
        else
            throw new IllegalArgumentException("Invalid category ID");
    }

    public void addCategoryDiscount(int categoryID, double discount) {
        if (discount < 0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        Category category = categoryMapper.getCategory(categoryID);
        if (category != null)
            category.addDiscount(discount);
        else
            throw new IllegalArgumentException("Invalid category ID");
    }

    public void addItemDiscount(int itemID, double discount) {

        if (itemID < 0)
            throw new IllegalArgumentException("invalid Item id");
        if (discount < 0 || discount > 100)
            throw new IllegalArgumentException("invalid discount amount");
        findItem(itemID).addDiscount(discount);
    }

    public Category addCategory(String name, int fatherID) {
        // checks - father id = 0 -> no father category
        // check that the father ID exists
        if (fatherID < 0)
            throw new IllegalArgumentException("invalid father category");
        Category FatherCategory = null;
        if (fatherID > 0) {
            FatherCategory = categoryMapper.getCategory(fatherID);
            if (FatherCategory == null)
                throw new IllegalArgumentException("No such father category");
        }
        Category toAdd = new Category(name, FatherCategory);
        if (toAdd.getFatherCategory() != null)
            toAdd.getFatherCategory().addSubCategory(toAdd);
        categoryMapper.addCategory(toAdd);
        return toAdd;
    }

    public Item getItemByLocation(int location) {
        return findItemByLocation(location);
    }

    public Item getItemById(int itemID) {
        return findItem(itemID);
    }

    public void changeAlertTime(int itemID, int daysAmount) {
        findItem(itemID).setAlertTime(daysAmount);
    }


    // -- private methods
    private Item findItem(int itemID) {
        for (Category cat : categoryMapper.getAllCategories()) {
            if (cat.getItems().containsKey(itemID))
                return cat.getItems().get(itemID);
        }
        throw new IllegalArgumentException("there is no item with this item ID");
    }

    private Item findItemByLocation(int location) {
        Item i = ItemsMapper.getInstance().getItemByLocation(location);
        if (i == null)
            throw new IllegalArgumentException("there is no item in this location");
        return i;
    }

    private boolean isAvailableLocation(int location) {
        if (location < 0)
            throw new IllegalArgumentException("invalid location");
        return ItemsMapper.getInstance().getItemByLocation(location) == null;
    }

    public ArrayList<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    public ArrayList<Category> getCategories(ArrayList<Integer> categoriesList) {
        ArrayList<Category> toReturn = new ArrayList<>();
        for (Category cat : categoryMapper.getAllCategories()) {
            if (categoriesList.contains(cat.getID()))
                toReturn.add(cat);
        }
        return toReturn;
    }

    public void deleteItem(int itemID) {
        boolean deleted = false;
        if (findItem(itemID).getContractList().size() != 0)
            throw new IllegalArgumentException("This item has contracts, can't delete it");
        List<Supplier> allSuppliers = SuppliersController.getInstance().getAllSuppliers();
        // check if the item exists in an order and if so remove it from the order
        for (Supplier s : allSuppliers) {
            List<Order> allOrdersBySupplier = s.getOrders();
            for (Order o : allOrdersBySupplier) {
                Set<ProductInOrder> allProductsInOrder = o.getProductsInOrder();
                for (ProductInOrder p : allProductsInOrder) {
                    Contract c = p.getContract();
                    if (c.getProduct().getId() == itemID) {
                        o.removeProduct(c, s.getDiscountsByPrice());
                    }
                }
            }
        }
        for (Category cat : categoryMapper.getAllCategories()) {
            if (cat.deleteItem(itemID)) {
                deleted = true;
                Item toDelete = this.findItem(itemID);
                ItemsMapper.getInstance().deleteItem(toDelete);
            }
        }
        if (!deleted)
            throw new IllegalArgumentException("Item ID does not exist");

    }

    public void removeFaultyItems() {
        for (Category category : categoryMapper.getAllCategories()) {
            for (Item item : category.getItems().values()) {
                item.removeFaultyItems();
                if (item.getAmount() < item.getMinAmount()) {
                    addOrder(item);
                }
            }
        }
    }

    public void sellItem(int itemID, int quantity) {
        Item item = findItem(itemID);
        if (quantity < 1)
            throw new IllegalArgumentException("invalid quantity");
        if (item.getAmount() < quantity)
            throw new IllegalArgumentException("No Available items for sale");
        if (item.SaleItem(quantity))
            addOrder(item);
    }

    private void addOrder(Item item) {
        Pair<Integer, Integer> supIDAndCatID = item.getCheapestSupplier();
        SuppliersController controller = SuppliersController.getInstance();
        //issuing an order in case of low quantity to a week from now
        int newOrderID = controller.openOrder(supIDAndCatID.first, LocalDate.now().plusWeeks(1), false);
        controller.addItemToOrder(supIDAndCatID.first, newOrderID, item.getMinAmount() - item.getAmount() + 1, supIDAndCatID.second);
    }
}