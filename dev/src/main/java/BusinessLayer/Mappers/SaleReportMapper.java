package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.SaleReport;
import DTO.SaleReportDTO;
import DataAccessLayer.SaleReportDAO;

import java.util.HashMap;

public class SaleReportMapper {

    private static SaleReportMapper instance=null;
    private SaleReportDAO dao;
    private HashMap<Integer, SaleReport> saleReportMapper;

    private SaleReportMapper(){
        dao=new SaleReportDAO();
        saleReportMapper=new HashMap<>();
    }

    public static SaleReportMapper getInstance(){
        if(instance==null)
            instance=new SaleReportMapper();
        return instance;
    }

    public SaleReport buildSaleReport(SaleReportDTO dto){
        return null;
        //@TODO: continue from here
    }
}
