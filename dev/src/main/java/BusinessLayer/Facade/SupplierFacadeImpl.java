package BusinessLayer.Facade;

import BusinessLayer.InventoryModule.StockController;
import BusinessLayer.SuppliersModule.*;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import DTO.ContractDTO;
import DTO.OrderDTO;
import DTO.SupplierDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//this class is an implementation of the Facade interface. each method passes on the work to the supplier controller or to the
//inventory depending on the method and returns an appropriate response object based on the success or failure of the operation.

public class SupplierFacadeImpl implements ISuppliersFacade {
    private SuppliersController suppliersController;


    public SupplierFacadeImpl(){
        suppliersController=SuppliersController.getInstance();
    }

    @Override
    public Response<Integer> addSupplier(String supplierName, Set<DayOfWeek> supplyingDays, boolean selfPickup, String bankAccount, PaymentAgreement paymentMethod, Set<String> categories, Set<String> manufactures, Map<String, String> contactInfo, Map<Double, Integer> discounts) {
        try {
             return new Response<>(suppliersController.addSupplier(supplierName,supplyingDays,selfPickup,bankAccount,paymentMethod,categories,manufactures,contactInfo,discounts));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteSupplier(int supplierId) {
        try {
            suppliersController.deleteSupplier(supplierId);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<SupplierDTO> getSupplier(int supplierId) {
        try {
            Supplier supplier = suppliersController.getSupplier(supplierId);
            return new Response<>(new SupplierDTO(supplier));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<SupplierDTO>> getAllSuppliers() {
        try {
            ArrayList<SupplierDTO> output=new ArrayList<>();
            for(Supplier s:suppliersController.getAllSuppliers())
                output.add(new SupplierDTO(s));
            return new Response<>(output);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> updateSupplier(SupplierDTO dto) {
        try {
            suppliersController.getSupplier(dto.getSupplierID()).updateSupplier(dto);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }


    @Override
    public Response<Boolean> addDiscount(int supplierId, double price, int discountPerecentage) {
        try {
            suppliersController.addDiscount(supplierId,price,discountPerecentage);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Integer> openOrder(int supplierId, LocalDate date, boolean isFixed) {
        try {
            return new Response<>(suppliersController.openOrder(supplierId,date,isFixed));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Integer> reOrder(int supplierID, int orderID, LocalDate date) {
        try {
            return new Response<>(suppliersController.reOrder(supplierID,orderID,date));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addItemToOrder(int supplierId, int orderId, int quantity, int supplierProductId) {
        try {
            suppliersController.addItemToOrder(supplierId,orderId,quantity,supplierProductId);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<OrderDTO> getOrder(int supplierID, int orderID) {
        try {
            return new Response(new OrderDTO(suppliersController.getOrder(supplierID,orderID),supplierID));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> receiveOrder(int supplierID, int orderID) {
        try {
            suppliersController.receiveOrder(supplierID,orderID);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<OrderDTO>> getOrdersBySupplier(int supplierId) {
        try {
            ArrayList<OrderDTO> list=new ArrayList<>();
            for(Order o:suppliersController.getOrdersBySupplier(supplierId))
                list.add(new OrderDTO(o,supplierId));
            return new Response<>(list);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<List<ContractDTO>> getItemsBySupplier(int supplierID) {
        try {
            ArrayList<ContractDTO> list=new ArrayList<>();
            for(Contract c:suppliersController.getItemsBySupplier(supplierID))
                list.add(new ContractDTO(c,supplierID));
            return new Response<>(list);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addItemToSupplier(int supplierID, int StoreProductID, int supplierProductID, double price, Map<Integer, Integer> quantityAgreement) {
        try {
            suppliersController.addItemToSupplier(supplierID, StockController.getInstance().getItemById(StoreProductID),supplierProductID,price,quantityAgreement);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteItemFromSupplier(int supplierID, int supplierProductID) {
        try {
            suppliersController.deleteItemFromSupplier(supplierID,supplierProductID);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteSupplierDiscount(int supplierID, double price) {
        try {
            suppliersController.deleteSupplierDiscount(supplierID,price);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteProductDiscount(int supplierID, int catalogueID, int quantity) {
        try {
            suppliersController.deleteProductDiscount(supplierID,catalogueID,quantity);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> cancelOrder(int supplierID, int orderID) {
        try {
            suppliersController.cancelOrder(supplierID,orderID);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> deleteProductFromOrder(int supplierID, int orderID, int productID) {
        try {
            suppliersController.deleteProductFromOrder(supplierID,orderID,productID);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return  new Response<>(e);
        }
    }

    @Override
    public Response<Boolean> addDiscountProduct(int supplierID, int catalogueID, int quantity, int discount) {
        try {
            suppliersController.getSupplier(supplierID).addDiscount(catalogueID,quantity,discount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }
}
