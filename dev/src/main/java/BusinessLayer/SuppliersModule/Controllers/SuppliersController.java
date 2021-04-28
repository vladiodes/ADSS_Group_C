package BusinessLayer.SuppliersModule.Controllers;

import java.time.LocalDateTime;
import java.util.*;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.SuppliersModule.*;


/**
 * this class manages the suppliers. it saves a map of the suppliers by their id's.
 * each method will find the wanted supplier in the map (or throw an exception if he doesn't exist)
 * and pass on the work to him.
 * the class also maintains a currID data member for issuing new supplier id's and a data member currOrderID
 * for issuing new orderID's.
 */


public class SuppliersController {
    private Map<Integer, Supplier> supplierMap;
    private int currID;
    private int currOrderID;

    public SuppliersController() {
        supplierMap = new HashMap<>();
        currID = 0;
        currOrderID = 0;
    }

    /**
     * Adds a new supplier to the system
     * @param supplierName
     * @param supplyingDays
     * @param selfPickup
     * @param bankAccount
     * @param paymentMethod
     * @param categories
     * @param manufactures
     * @param contactInfo
     * @param discounts
     * @return the id of the new added supplier
     */
    public int addSupplier(String supplierName, Set<DayOfWeek> supplyingDays, boolean selfPickup, String bankAccount, PaymentAgreement paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
        for(Supplier s:supplierMap.values()){
            if(s.getSupplierName()==supplierName)
                throw new IllegalArgumentException("There's already a supplier with that name in the system");
        }
            supplierMap.put(currID, new Supplier(currID, supplierName, supplyingDays, selfPickup, bankAccount, paymentMethod, categories, manufactures, contactInfo, discounts));
            currID++;
            return currID-1;
    }

    /**
     * Deletes a supplier from the system
     * @param supplierID
     */
    public void deleteSupplier(int supplierID) {
        Supplier deleted = supplierMap.remove(supplierID);
        if (deleted == null) {
            throw new IllegalArgumentException("A supplier with that id doesn't exist in the system.");
        }
    }

    /**
     *
     * @param supplierID
     * @return the supplier with the give id
     */
    public Supplier getSupplier(int supplierID) {
        return search(supplierID);
    }

    /**
     *
     * @return a list of all suppliers in the system
     */
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new LinkedList<>();
        for (Supplier supplier :
                supplierMap.values()) {
            suppliers.add(supplier);
        }
        return suppliers;
    }

    /**
     * adds a discount to the supplier
     * @param supplierId
     * @param price
     * @param discountPerecentage
     */
    public void addDiscount(int supplierId, double price, int discountPerecentage) {
        search(supplierId).addDiscount(price, discountPerecentage);
    }

    /**
     * issues a new order
     * @param supplierId
     * @param date
     * @param isFixed
     * @return the id of the issued order
     */
    public int openOrder(int supplierId, LocalDateTime date, boolean isFixed) {
        Supplier s = search(supplierId);
            s.addOrder(date, isFixed, currOrderID);
            currOrderID++;
            return currOrderID-1;
    }

    /**
     * Re orders a new order
     * @param supplierID
     * @param orderID
     * @param date
     * @return the id of the new order
     */
    public int reOrder(int supplierID, int orderID, LocalDateTime date) {
        Supplier s =search(supplierID);
            s.reOrder(currOrderID, orderID, date);
            currOrderID++;
            return currOrderID-1;
    }

    /**
     * adds an item to a given order
     * @param supplierId
     * @param orderId
     * @param quantity
     * @param supplierProductId
     */
    public void addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        Supplier s = search(supplierId);
            s.addItemToOrder(orderId,quantity,supplierProductId);

    }

    /**
     *
     * @param supplierID
     * @param orderID
     * @return the order object related with the given ids
     */
    public Order getOrder(int supplierID, int orderID) {
        return search(supplierID).getOrder(orderID);
    }

    /**
     * receives the order
     * @param supplierID
     * @param orderID
     */
    public void receiveOrder(int supplierID, int orderID) {
        Supplier s = search(supplierID);
        s.receiveOrder(orderID);
    }

    /**
     *
     * @param supplierId
     * @return returns a list of all orders issues with the given supplier id
     */
    public List<Order> getOrdersBySupplier(int supplierId) {
        Supplier s =search(supplierId);
        return s.getOrders();
    }

    /**
     *
     * @param supplierID
     * @return returns a list with all the products (contracts) issued with the supplier
     */
    public List<Contract> getItemsBySupplier(int supplierID){
        return search(supplierID).getSuppliedItems();
    }

    /**
     * Adds a new item to the supplier
     * @param supplierID
     * @param product
     * @param supplierProductID
     * @param price
     * @param quantityAgreement
     */
    public void addItemToSupplier(int supplierID, Item product, int supplierProductID, double price, Map<Integer, Integer> quantityAgreement){
        Supplier s = search(supplierID);
        s.addContract(product,supplierProductID,price,quantityAgreement);
    }

    /**
     * Deletes an item from a supplier
     * @param supplierID
     * @param supplierProductID
     */
    public void deleteItemFromSupplier(int supplierID, int supplierProductID) {
        search(supplierID).removeContract(supplierProductID);
    }

    /**
     * Deletes a discount from a supplier
     * @param supplierID
     * @param price
     */
    public void deleteSupplierDiscount(int supplierID, double price){
        search(supplierID).removeDiscount(price);
    }

    /**
     * Deletes a discount from a supplier's contract
     * @param supplierID
     * @param catalogueID
     * @param quantity
     */
    public void deleteProductDiscount(int supplierID, int catalogueID, int quantity){
        search(supplierID).deleteProductDiscount(catalogueID,quantity);
    }

    /**
     * Cancels an order
     * @param supplierID
     * @param orderID
     */
    public void cancelOrder(int supplierID, int orderID){
        search(supplierID).cancelOrder(orderID);
    }

    /**
     * Deletes a product from an order
     * @param supplierID
     * @param orderID
     * @param productID
     */
    public void deleteProductFromOrder(int supplierID, int orderID, int productID){
        search(supplierID).deleteProductFromOrder(orderID,productID);
    }

    //this function searches for a supplier in the map and throws an exception if not found.
    private Supplier search(int ID){
        Supplier s = supplierMap.get(ID);
        if (s == null) {
            throw new IllegalArgumentException("A supplier with that id doesn't exist in the system.");
        }
        return s;
    }
}