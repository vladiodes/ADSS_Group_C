package BusinessLayer.Facade;

import BusinessLayer.Controllers.Inventory;
import BusinessLayer.Controllers.SuppliersController;

import java.util.List;
import java.util.Map;

public class FacadeImpl implements ISuppliersFacade {
    private Inventory inventory;
    private SuppliersController suppliersController;


    public FacadeImpl(){
        inventory=new Inventory();
        suppliersController=new SuppliersController();
    }

    @Override
    public Response<Boolean> addSupplier(String supplierName, List<Integer> supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, List<String> categories, List<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
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
}
