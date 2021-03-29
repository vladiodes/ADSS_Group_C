package BusinessLayer.Controllers;

import java.util.*;

import BusinessLayer.Facade.Response;
import BusinessLayer.Order;
import BusinessLayer.Supplier;

public class SuppliersController {
    private Map<Integer, Supplier> supplierMap;
    private int currID;
    private int currOrderID;

    public SuppliersController() {
        supplierMap = new HashMap<>();
        currID = 0;
        currOrderID = 0;
    }

    public Response<Boolean> addSupplier(String supplierName, Set<Integer> supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
        try {
            supplierMap.put(currID, new Supplier(currID, supplierName, supplyingDays, selfPickup, bankAccount, paymentMethod, categories, manufactures, contactInfo, discounts));
            currID++;
            return new Response<>(true);
        } catch (Exception e) {
            return new Response<>(e);
        }
    }

    public Response<Boolean> deleteSupplier(int supplierID) {
        Supplier deleted = supplierMap.remove(supplierID);
        if (deleted == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        return new Response<>(true);
    }

    public Response<String> getSupplier(int supplierID) {
        Supplier s = supplierMap.get(supplierID);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        return new Response<>(s.toString());
    }

    public Response<List<String>> getAllSuppliers() {
        List<String> suppliersStrings = new LinkedList<>();
        for (Integer id :
                supplierMap.keySet()) {
            suppliersStrings.add(supplierMap.get(id).toString());
        }
        return new Response<>(suppliersStrings);
    }

    public Response<Boolean> updateSuppliersShippingStatus(int supplierID, boolean selfPickUp) {
        Supplier s = supplierMap.get(supplierID);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.setSelfPickUp(selfPickUp);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    public Response<Boolean> updateSuppliersFixedDays(int supplierID, Set<Integer> newFixedDays) {
        Supplier s = supplierMap.get(supplierID);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.setFixedDays(newFixedDays);
            return new Response<>(true);
        } catch (Exception e) {
            return new Response<>(e);
        }
    }

    public Response<Boolean> addDiscount(int supplierId, double price, int discountPerecentage) {
        Supplier s = supplierMap.get(supplierId);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.addDiscount(price, discountPerecentage);
            return new Response<>(true);
        } catch (Exception e) {
            return new Response<>(e);
        }
    }

    public Response<Integer> openOrder(int supplierId, Date date, boolean isFixed) {
        Supplier s = supplierMap.get(supplierId);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.addOrder(date, isFixed, currOrderID);
            currOrderID++;
        }
        catch (Exception e){
            return new Response<>(e);
        }
        return new Response<>(currOrderID - 1);
    }

    public Response<Integer> reOrder(int supplierID, int orderID, Date date) {
        Supplier s = supplierMap.get(supplierID);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.reOrder(currOrderID, orderID, date);
            currOrderID++;
        }
        catch (Exception e){
            return new Response<>(e);
        }
        return new Response<>(currOrderID - 1);
    }

    public Response<Boolean> addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        Supplier s = supplierMap.get(supplierId);
        if (s == null) {
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        try {
            s.addItemToOrder(orderId,quantity,supplierProductId);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }
}