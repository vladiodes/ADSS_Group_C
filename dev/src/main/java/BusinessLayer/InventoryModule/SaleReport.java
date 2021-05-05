package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.SaleReportMapper;
import DTO.SaleReportDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleReport extends Report
{
    private List<Sale> sales;
    public SaleReport(ArrayList<Sale> sales, LocalDate startDate, LocalDate endDate) {
        super(-1);
        setStartDate(startDate);
        setEndDate(endDate);
        this.sales = new ArrayList<>();
        for (Sale sale : sales) this.sales.add(sale);
        setReportID(SaleReportMapper.getInstance().addReport(this));
    }
    public SaleReport(SaleReportDTO dto,List<Sale> sales){
        super(dto.reportID);
        setStartDate(dto.startDate);
        setEndDate(dto.endDate);
        this.sales=sales;
    }

    public List<Sale> getSales() {
        return this.sales;
    }
}
