package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Sale;
import DTO.SaleDTO;
import DataAccessLayer.SaleDAO;

import java.util.HashMap;

public class SaleMapper {
    private static SaleMapper instance=null;
    private HashMap<Integer, Sale> saleMapper;
    private SaleDAO dao;
    private SaleMapper(){
        saleMapper=new HashMap<>();
        dao=new SaleDAO();
    }
    public static SaleMapper getInstance(){
        if(instance==null)
            instance=new SaleMapper();
        return instance;
    }

    public Sale buildSale(SaleDTO dto){
        Sale sale=new Sale(dto);
        saleMapper.put(sale.getSaleID(),sale);
        return sale;
    }

    public int addSale(Sale sale){
        int id=dao.insert(new SaleDTO(sale));
        saleMapper.put(id,sale);
        return id;
    }

    public void updateSale(Sale sale){
        //shouldn't be updated actually
    }

    public Sale getSale(int saleID){
        if(saleMapper.containsKey(saleID))
            return saleMapper.get(saleID);
        SaleDTO dto=dao.get(saleID);
        if(dto==null)
            throw new IllegalArgumentException("No such sale in the database");
        return buildSale(dto);
    }
}
