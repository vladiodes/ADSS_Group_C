package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Category;
import BusinessLayer.SuppliersModule.Contract;
import DTO.CategoryDTO;
import DTO.ContractDTO;
import DTO.ItemDTO;
import DataAccessLayer.ContractDAO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContractMapper {
    private static ContractMapper instance=null;
    private HashMap<Integer, HashMap<Integer,Contract>> contractMapper;//every supplier id has a map of his contracts by the item id
    private ContractDAO dao;

    private ContractMapper(){
        contractMapper=new HashMap<>();
        dao=new ContractDAO();
    }

    public static ContractMapper getInstance(){
        if(instance==null){
            instance=new ContractMapper();
        }
        return instance;
    }

    public Contract buildContract(ContractDTO dto){
        if(dto==null){
            return null;
        }
        //@todo: recursive build stack overflow(FINISHED)
        Contract contract=new Contract(dto.pricePerUnit,dto.supplierID,dto.catalogueID,dto.discountByQuantity,ItemsMapper.getInstance().getItem(dto.storeID));
        return contract;
    }

    public Contract getContract(int SupplierID,int CatalogueIDbySupplier,int ItemID){
        if(SupplierID<=0 | CatalogueIDbySupplier<=0 | ItemID<=0){
            return null;
        }
        if(contractMapper.containsKey(SupplierID)){
            if(contractMapper.get(SupplierID).containsKey(ItemID)){
                return contractMapper.get(SupplierID).get(ItemID);
            }
        }
        ContractDTO contract=dao.get(SupplierID, CatalogueIDbySupplier, ItemID);
        return buildContract(contract);
    }

    public List<Contract> getItemContracts(int itemID){
        ArrayList<Contract> output=new ArrayList<>();
        for(Contract c: getAllContracts()){
            if(c.getProduct().getId()==itemID)
                output.add(c);
        }
        return output;
    }

    private List<Contract> getAllContracts() {
            ArrayList<Contract> output=new ArrayList<>();
            for(ContractDTO dto:dao.getAllContracts()){
                output.add(buildContract(dto));
            }
            return output;
    }

    public int addContract(Contract contract,int supplierID){
        int id=dao.insert(new ContractDTO(contract,supplierID));
        if(!contractMapper.containsKey(supplierID)){
            contractMapper.put(supplierID,new HashMap<>());
        }
            contractMapper.get(supplierID).put(contract.getProduct().getId(),contract);
        return id;
    }

    public void remove(Contract contract) {
        HashMap<Integer,Contract> contracts = contractMapper.get(contract.getSupplierID());
        contracts.remove(contract.getProduct().getId());
        dao.delete(contract.getSupplierID(),contract.getCatalogueIDBySupplier(),contract.getProduct().getId());
    }

    public void removeDiscount(Contract c, int quantity) {
        dao.removeDiscount(new ContractDTO(c,c.getSupplierID()),quantity);
    }
    public void addDiscount(Contract c,int quantity,int discount)
    {
        dao.insertDiscount(new ContractDTO(c,c.getSupplierID()),quantity,discount);
    }
}
