package BusinessLayer.Facade;

import BusinessLayer.Controllers.Inventory;
import BusinessLayer.Controllers.SuppliersController;
import BusinessLayer.Supplier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

//this class is an implementation of the Facade interface. each method passes on the work to the supplier controller or to the
//inventory depending on the method and returns an appropriate response object based on the success or failure of the operation.

public class FacadeImpl implements ISuppliersFacade {
    private Inventory inventory;
    private SuppliersController suppliersController;


    public FacadeImpl(){
        inventory=new Inventory();
        suppliersController=new SuppliersController();
    }

    @Override
    public Response<Boolean> addSupplier(String supplierName, Set<Supplier.DayOfWeek> supplyingDays, boolean selfPickup, String bankAccount, Supplier.PaymentAgreement paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
        try {
             suppliersController.addSupplier(supplierName,supplyingDays,selfPickup,bankAccount,paymentMethod,categories,manufactures,contactInfo,discounts);
             return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteSupplier(int supplierId) {
        try {
            suppliersController.deleteSupplier(supplierId);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<String> getSupplier(int supplierId) {
        try {
             return new Response<>(suppliersController.getSupplier(supplierId));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<String>> getAllSuppliers() {
        try {
            return new Response<>(suppliersController.getAllSuppliers());
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> updateSuppliersShippingStatus(int supplierID, boolean selfPickUp) {
        try {
            suppliersController.updateSuppliersShippingStatus(supplierID,selfPickUp);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> updateSuppliersFixedDays(int supplierID, Set<Supplier.DayOfWeek> newFixedDays) {
        try {
            suppliersController.updateSuppliersFixedDays(supplierID,newFixedDays);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addDiscount(int supplierId, double price, int discountPerecentage) {
        try {
            suppliersController.addDiscount(supplierId,price,discountPerecentage);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Integer> openOrder(int supplierId, LocalDateTime date, boolean isFixed) {
        try {
            return new Response<>(suppliersController.openOrder(supplierId,date,isFixed));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Integer> reOrder(int supplierID, int orderID, LocalDateTime date) {
        try {
            return new Response<>(suppliersController.reOrder(supplierID,orderID,date));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        try {
            suppliersController.addItemToOrder(supplierId,orderId,quantity,supplierProductId);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<String>> getOrder(int supplierID, int orderID) {
        try {
            return new Response<>(suppliersController.getOrder(supplierID,orderID));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> receiveOrder(int supplierID, int orderID) {
        try {
            suppliersController.receiveOrder(supplierID,orderID);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<Integer>> getOrderIdsBySupplier(int supplierId) {
        try {
            return new Response<>(suppliersController.getOrderIdsBySupplier(supplierId));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<String>> getItemsBySupplier(int supplierID) {
        try {
            return new Response<>(suppliersController.getItemsBySupplier(supplierID));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Integer> addItemToStore(String productName) {
        try {
            return new Response<>(inventory.addItemToStore(productName));
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addItemToSupplier(int supplierID, int StoreProductID, int supplierProductID, double price, Map<Integer, Integer> quantityAgreement) {
        try {
            suppliersController.addItemToSupplier(supplierID,inventory.getProductByID(StoreProductID),supplierProductID,price,quantityAgreement);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteItemFromSupplier(int supplierID, int supplierProductID) {
        try {
            suppliersController.deleteItemFromSupplier(supplierID,supplierProductID);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteSupplierDiscount(int supplierID, double price) {
        try {
            suppliersController.deleteSupplierDiscount(supplierID,price);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteProductDiscount(int supplierID, int productID, int quantity) {
        try {
            suppliersController.deleteProductDiscount(supplierID,productID,quantity);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> cancelOrder(int supplierID, int orderID) {
        try {
            suppliersController.cancelOrder(supplierID,orderID);
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteProductFromOrder(int supplierID, int orderID, int productID) {
        try {
            suppliersController.deleteProductFromOrder(supplierID,orderID,productID);
            return new Response<>(true);
        }
        catch (Exception e){
            return  new Response<>(e);
        }
    }
}
