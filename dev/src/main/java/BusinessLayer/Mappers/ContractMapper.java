package BusinessLayer.Mappers;

import BusinessLayer.SuppliersModule.Contract;
import DTO.ContractDTO;
import DataAccessLayer.ContractDAO;


import java.util.HashMap;

public class ContractMapper {
    private static ContractMapper instance=null;
    private HashMap<Integer, HashMap<Integer,Contract>> contractMapper;//every supplier id has a map of his contracts by the item id
    private ContractDAO dao;

    private ContractMapper(){
        contractMapper=new HashMap<>();
        dao=new ContractDAO();
    }

    public ContractMapper getInstance(){
        if(instance==null){
            instance=new ContractMapper();
        }
        return instance;
    }

    public Contract buildContract(ContractDTO dto){
        if(dto==null){
            return null;
        }
        Contract contract=new Contract(dto.pricePerUnit,dto.catalogueID,dto.discountByQuantity,ItemsMapper.getInstance().getItem(dto.storeID));
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

    public void addContract(Contract contract,int supplierID){
        int id=dao.insert(new ContractDTO(contract,supplierID));
        if(!contractMapper.containsKey(supplierID)){
            contractMapper.put(supplierID,new HashMap<>());
        }
            contractMapper.get(supplierID).put(contract.getProduct().getId(),contract);
    }
}
