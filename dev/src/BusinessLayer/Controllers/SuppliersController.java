package BusinessLayer.Controllers;

import java.time.LocalDateTime;
import java.util.*;

import BusinessLayer.Product;
import BusinessLayer.Supplier;

//this class manages the suppliers. it saves a map of the suppliers by their id's.
//each method will find the wanted supplier in the map (or throw an exception if he doesn't exist)
//and pass on the work to him.
//the class also maintains a currID data member for issuing new supplier id's and a data member currOrderID
//for issuing new orderID's.


public class SuppliersController {
    private Map<Integer, Supplier> supplierMap;
    private int currID;
    private int currOrderID;

    public SuppliersController() {
        supplierMap = new HashMap<>();
        currID = 0;
        currOrderID = 0;
    }

    public void addSupplier(String supplierName, Set<Integer> supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
            supplierMap.put(currID, new Supplier(currID, supplierName, supplyingDays, selfPickup, bankAccount, paymentMethod, categories, manufactures, contactInfo, discounts));
            currID++;
    }

    public void deleteSupplier(int supplierID) {
        Supplier deleted = supplierMap.remove(supplierID);
        if (deleted == null) {
            throw new IllegalArgumentException("A supplier with that id doesn't exist in the system.");
        }
    }

    public String getSupplier(int supplierID) {
        Supplier s = search(supplierID);
        return s.toString();
    }

    public List<String> getAllSuppliers() {
        List<String> suppliersStrings = new LinkedList<>();
        for (Integer id :
                supplierMap.keySet()) {
            suppliersStrings.add(supplierMap.get(id).toString());
        }
        return suppliersStrings;
    }

    public void updateSuppliersShippingStatus(int supplierID, boolean selfPickUp) {
        Supplier s = search(supplierID);
            s.setSelfPickUp(selfPickUp);
    }

    public void updateSuppliersFixedDays(int supplierID, Set<Integer> newFixedDays) {
        Supplier s = search(supplierID);
            s.setFixedDays(newFixedDays);
    }

    public void addDiscount(int supplierId, double price, int discountPerecentage) {
        Supplier s = search(supplierId);
            s.addDiscount(price, discountPerecentage);
    }

    public int openOrder(int supplierId, LocalDateTime date, boolean isFixed) {
        Supplier s = search(supplierId);
            s.addOrder(date, isFixed, currOrderID);
            currOrderID++;
            return currOrderID-1;
    }

    public int reOrder(int supplierID, int orderID, LocalDateTime date) {
        Supplier s =search(supplierID);
            s.reOrder(currOrderID, orderID, date);
            currOrderID++;
            return currOrderID-1;
    }

    public void addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        Supplier s = search(supplierId);
            s.addItemToOrder(orderId,quantity,supplierProductId);
    }

    public List<String> getOrder(int supplierID, int orderID) {
        Supplier s = search(supplierID);
        return s.getOrder(orderID);
    }

    public void receiveOrder(int supplierID, int orderID) {
        Supplier s = search(supplierID);
        s.receiveOrder(orderID);
    }

    public List<Integer> getOrderIdsBySupplier(int supplierId) {
        Supplier s =search(supplierId);
        return s.getOrdersIDs();
    }

    public List<String> getItemsBySupplier(int supplierID){
        Supplier s = search(supplierID);
        return s.getSuppliedItems();
    }

    public void addItemToSupplier(int supplierID, Product product, int supplierProductID, double price, Map<Integer, Integer> quantityAgreement){
        Supplier s = search(supplierID);
        s.addContract(product,supplierProductID,price,quantityAgreement);
    }

    public void deleteItemFromSupplier(int supplierID, int supplierProductID){
        Supplier s = search(supplierID);
        s.removeContract(supplierProductID);
    }

    public void deleteSupplierDiscount(int supplierID, double price){
        Supplier s = search(supplierID);
        s.removeDiscount(price);
    }

    public void deleteProductDiscount(int supplierID, int productID, int quantity){
        Supplier s = search(supplierID);
        s.deleteProductDiscount(productID,quantity);
    }

    public void cancelOrder(int supplierID, int orderID){
        Supplier s = search(supplierID);
        s.cancelOrder(orderID);
    }

    public void deleteProductFromOrder(int supplierID, int orderID, int productID){
        Supplier s=search(supplierID);
        s.deleteProductFromOrder(orderID,productID);
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