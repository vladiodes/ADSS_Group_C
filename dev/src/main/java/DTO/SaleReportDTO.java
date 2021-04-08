package DTO;

import BusinessLayer.Sale;
import BusinessLayer.SaleReport;
import DTO.ReportDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaleReportDTO extends ReportDTO {
    private List<SaleDTO> sales;
    public SaleReportDTO(SaleReport report) {
        super(report);
        this.sales = new ArrayList<>();
        for(Sale sale : report.getSales()){
            this.sales.add(new SaleDTO(sale));

        }
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        builder.append("Report ID: "+ this.reportID + "\n");
        builder.append("Report Start Date: "+ this.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report End Date: "+ this.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report Sales: \n");
        for(SaleDTO s : sales)
        {
            builder.append(s.toString());
        }
        return builder.toString();


    }
}
