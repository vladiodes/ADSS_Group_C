package BusinessLayer.Controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import BusinessLayer.Facade.Response;
import BusinessLayer.Supplier;

public class SuppliersController{
    private Map<Integer, Supplier> supplierMap;
    private int currID;

    public SuppliersController(){
        supplierMap=new HashMap<>();
        currID=0;
    }

    public Response<Boolean> addSupplier(String supplierName, List<Integer> supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, List<String> categories, List<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts){
        try {
            supplierMap.put(currID,new Supplier(currID,supplierName,supplyingDays,selfPickup,bankAccount,paymentMethod,categories,manufactures,contactInfo,discounts));
            currID++;
            return new Response<>(true);
        }
        catch (Exception e){
            return new Response<>(e);
        }
    }

    public Response<Boolean> deleteSupplier(int supplierID){
        Supplier deleted=supplierMap.remove(supplierID);
        if(deleted==null){
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        return new Response<>(true);
    }

    public Response<String> getSupplier(int supplierID){
        Supplier s=supplierMap.get(supplierID);
        if(s==null){
            return new Response<>(new IllegalArgumentException("A supplier with that id doesn't exist in the system."));
        }
        return new Response<>(s.toString());
    }

    public Response<List<String>> getAllSuppliers(){
        List<String> suppliersStrings=new LinkedList<>();
        for (Integer id:
             supplierMap.keySet()) {
            suppliersStrings.add(supplierMap.get(id).toString());
        }
        return new Response<>(suppliersStrings);
    }
}