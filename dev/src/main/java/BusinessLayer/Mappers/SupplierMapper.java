package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Category;
import BusinessLayer.SuppliersModule.Supplier;
import DTO.CategoryDTO;
import DTO.SupplierDTO;
import DataAccessLayer.SupplierDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierMapper {
    private static SupplierMapper instance=null;
    private HashMap<Integer, Supplier> supplierMapper;
    private SupplierDAO dao;

    private SupplierMapper(){
        supplierMapper=new HashMap<>();
        dao=new SupplierDAO();
    }

    public static SupplierMapper getInstance(){
        if(instance==null){
            instance=new SupplierMapper();
        }
        return instance;
    }

    public Supplier buildSupplier(SupplierDTO dto){
        if(dto==null){
            return null;
        }
        Supplier supplier=new Supplier(dto);
        for (Integer orderID:
             dto.orderIDs) {
                supplier.addOrder(OrderMapper.getInstance().getOrder(orderID));
        }
        for (Integer itemID:
             dto.contracts.keySet()) {
                supplier.addContract(ContractMapper.getInstance().getContract(dto.getSupplierID(),dto.contracts.get(itemID),itemID));
        }
        return supplier;
    }

    public Supplier getSupplier(int id){
        if(id<=0){
            return null;
        }
        if(supplierMapper.containsKey(id)){
            return supplierMapper.get(id);
        }
        SupplierDTO supplier=dao.get(id);
        return buildSupplier(supplier);
    }

    public int addSupplier(Supplier supplier){
        int id=dao.insert(new SupplierDTO(supplier));
        supplierMapper.put(supplier.getSupplierID(),supplier);
        return id;
    }

    public List<Supplier> getAllSuppliers() {
            ArrayList<Supplier> output=new ArrayList<>();
            for(SupplierDTO dto:dao.getAllSuppliers()){
                output.add(buildSupplier(dto));
            }
            return output;
    }

    public void deleteSupplier(Supplier toDelete) {
        dao.delete(new SupplierDTO(toDelete));
        supplierMapper.remove(toDelete.getSupplierID());
    }

    public void removeDiscount(Supplier s, double price) {
        dao.removeDiscount(s.getSupplierID(),price);
    }

    public void update(Supplier supplier) {
        dao.update(new SupplierDTO(supplier));
    }

    public void addDiscount(Supplier s, double price, int discountPerecentage) {
        dao.addDiscount(s.getSupplierID(),price,discountPerecentage);
    }
}
