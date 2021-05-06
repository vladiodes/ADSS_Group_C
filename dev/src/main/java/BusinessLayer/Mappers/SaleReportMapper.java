package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Report;
import BusinessLayer.InventoryModule.Sale;
import BusinessLayer.InventoryModule.SaleReport;
import DTO.SaleReportDTO;
import DataAccessLayer.SaleReportDAO;

import java.util.ArrayList;
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
        ArrayList<Sale> salesInReport = new ArrayList<>();
        for (int id : dto.saleIDS) {
            Sale sale = SaleMapper.getInstance().getSale(id);
            if (sale != null)
                salesInReport.add(sale);
        }
        SaleReport report = new SaleReport(dto,salesInReport);
        saleReportMapper.put(report.getReportID(), report);
        return report;
    }

    public int addReport(SaleReport report){
        int id=dao.insert(new SaleReportDTO(report));
        saleReportMapper.put(id,report);
        report.setReportID(id);
        return id;
    }

    public void updateReport(SaleReport report){
        dao.update(new SaleReportDTO(report));
    }

    public Report getReport(int reportID){
        if(saleReportMapper.containsKey(reportID))
            return saleReportMapper.get(reportID);
        SaleReportDTO dto=dao.get(reportID);
        if(dto==null)
            throw new IllegalArgumentException("No such report in the system");
        return buildSaleReport(dto);
    }
}
