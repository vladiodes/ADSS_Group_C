package BusinessLayer.Mappers;

import BusinessLayer.SuppliersModule.Supplier;
import DTO.SupplierDTO;
import DataAccessLayer.SupplierDAO;

import java.util.HashMap;

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
        Supplier supplier=new Supplier(dto.getSupplierID(),dto.supplierName,dto.fixedDays,dto.selfPickUp,dto.bankAccount,dto.paymentMethod,dto.categories,dto.manufacturers,dto.contactInfo,dto.discountsByPrice);
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
}
