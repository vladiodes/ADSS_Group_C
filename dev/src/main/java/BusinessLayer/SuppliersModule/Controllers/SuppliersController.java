package BusinessLayer.SuppliersModule.Controllers;

import java.time.LocalDateTime;
import java.util.*;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.Mappers.ContractMapper;
import BusinessLayer.Mappers.OrderMapper;
import BusinessLayer.Mappers.SupplierMapper;
import BusinessLayer.SuppliersModule.*;


/**
 * this class manages the suppliers. it saves a map of the suppliers by their id's.
 * each method will find the wanted supplier in the map (or throw an exception if he doesn't exist)
 * and pass on the work to him.
 * the class also maintains a currID data member for issuing new supplier id's and a data member currOrderID
 * for issuing new orderID's.
 */


public class SuppliersController {
    private static SuppliersController instance = null;
    private SupplierMapper supplierMapper;

    private SuppliersController() {
        supplierMapper=SupplierMapper.getInstance();
    }

    public static SuppliersController getInstance()
    {
        if(instance == null)
            instance = new SuppliersController();
        return instance;
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
        for(Supplier s:supplierMapper.getAllSuppliers()){
            if(s.getSupplierName().equals(supplierName))
                throw new IllegalArgumentException("There's already a supplier with that name in the system");
        }
            Supplier supplier=new Supplier(supplierName, supplyingDays, selfPickup, bankAccount, paymentMethod, categories, manufactures, contactInfo, discounts);
            int id=supplierMapper.addSupplier(supplier);
            supplier.setSupplierID(id);
            return id;
    }

    /**
     * Deletes a supplier from the system
     * @param supplierID
     */
    public void deleteSupplier(int supplierID) {
        Supplier toDelete = supplierMapper.getSupplier(supplierID);
        if (toDelete == null)
            throw new IllegalArgumentException("A supplier with that id doesn't exist in the system.");
        supplierMapper.deleteSupplier(toDelete);
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
        return new LinkedList<>(supplierMapper.getAllSuppliers());
    }

    /**
     * adds a discount to the supplier
     * @param supplierId
     * @param price
     * @param discountPerecentage
     */
    public void addDiscount(int supplierId, double price, int discountPerecentage) {
        Supplier s=search(supplierId);
        s.addDiscount(price, discountPerecentage);
        supplierMapper.addDiscount(s,price,discountPerecentage);
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
            Order addedOrder=s.addOrder(date, isFixed);
            int id= OrderMapper.getInstance().addOrder(addedOrder,supplierId);
            addedOrder.setOrderID(id);
            return id;
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
            Order order=s.reOrder(orderID, date);
            int id=OrderMapper.getInstance().addOrder(order,supplierID);
            order.setOrderID(id);
            return id;
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
        ContractMapper.getInstance().addContract(s.addContract(product,supplierProductID,price,quantityAgreement),supplierID);
    }

    /**
     * Deletes an item from a supplier
     * @param supplierID
     * @param supplierProductID
     */
    public void deleteItemFromSupplier(int supplierID, int supplierProductID) {
        ContractMapper.getInstance().remove(search(supplierID).removeContract(supplierProductID));
    }

    /**
     * Deletes a discount from a supplier
     * @param supplierID
     * @param price
     */
    public void deleteSupplierDiscount(int supplierID, double price){
        Supplier s=search(supplierID);
        s.removeDiscount(price);
        supplierMapper.removeDiscount(s,price);
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
        Supplier s = supplierMapper.getSupplier(ID);
        if (s == null) {
            throw new IllegalArgumentException("A supplier with that id doesn't exist in the system.");
        }
        return s;
    }
}