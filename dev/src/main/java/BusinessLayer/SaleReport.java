package BusinessLayer;

import BusinessLayer.Report;
import BusinessLayer.Sale;
import DTO.SaleDTO;
import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.List;

public class SaleReport extends Report
{
    private List<Sale> sales;
    public SaleReport(int reportID,ArrayList<Sale> sales) {
        super(reportID);
        this.sales = new ArrayList<>();
        for(Sale sale : sales){
            this.sales.add(sale);
        }
    }

    public List<Sale> getSales() {
        return this.sales;
    }
}
