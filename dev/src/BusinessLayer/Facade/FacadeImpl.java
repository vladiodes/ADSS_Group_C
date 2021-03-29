package BusinessLayer.Facade;

import BusinessLayer.Controllers.Inventory;
import BusinessLayer.Controllers.SuppliersController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FacadeImpl implements ISuppliersFacade {
    private Inventory inventory;
    private SuppliersController suppliersController;


    public FacadeImpl(){
        inventory=new Inventory();
        suppliersController=new SuppliersController();
    }

    @Override
    public Response<Boolean> addSupplier(String supplierName, Set<Integer> supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
        return suppliersController.addSupplier(supplierName,supplyingDays,selfPickup,bankAccount,paymentMethod,categories,manufactures,contactInfo,discounts);
    }

    @Override
    public Response<Boolean> deleteSupplier(int supplierId) {
        return suppliersController.deleteSupplier(supplierId);
    }

    @Override
    public Response<String> getSupplier(int supplierId) {
        return suppliersController.getSupplier(supplierId);
    }

    @Override
    public Response<List<String>> getAllSuppliers() {
        return suppliersController.getAllSuppliers();
    }

    @Override
    public Response<Boolean> updateSuppliersShippingStatus(int supplierID, boolean selfPickUp) {
        return suppliersController.updateSuppliersShippingStatus(supplierID,selfPickUp);
    }

    @Override
    public Response<Boolean> updateSuppliersFixedDays(int supplierID, Set<Integer> newFixedDays) {
        return suppliersController.updateSuppliersFixedDays(supplierID,newFixedDays);
    }

    @Override
    public Response<Boolean> addDiscount(int supplierId, double price, int discountPerecentage) {
        return suppliersController.addDiscount(supplierId,price,discountPerecentage);
    }

    @Override
    public Response<Integer> openOrder(int supplierId, Date date, boolean isFixed) {
        return suppliersController.openOrder(supplierId,date,isFixed);
    }

    @Override
    public Response<Integer> reOrder(int supplierID, int orderID, Date date) {
        return suppliersController.reOrder(supplierID,orderID,date);
    }

    @Override
    public Response<Boolean> addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        return null;
    }
}
