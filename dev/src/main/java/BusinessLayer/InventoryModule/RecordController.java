package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.ReportMapper;
import BusinessLayer.Mappers.SaleMapper;
import BusinessLayer.Mappers.SaleReportMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordController {
    // -- fields
    private static RecordController recordControllerInstance = null;
    private SaleMapper salesMapper;
    private ReportMapper reportsMapper;
    private SaleReportMapper saleReportsMapper;

    // -- constructor
    private RecordController() {
        salesMapper=SaleMapper.getInstance();
        reportsMapper=ReportMapper.getInstance();
        saleReportsMapper=SaleReportMapper.getInstance();
    }

    public static RecordController getInstance(){
        if(recordControllerInstance == null)
            recordControllerInstance = new RecordController();
        return recordControllerInstance;
    }


    // if one of the specific items of the Item is expired we will add the item to the report
    public Report showExpItems(ArrayList<Category> categories){
        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                List<SpecificItem> items = value.getSpecificItems();
                for(SpecificItem item : items)
                {
                    if (item.isExp(value.getAlertTime()) & !item.isFaulty())
                    {
                        itemsInReport.add(value);
                        break;
                    }
                }

            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        reportsMapper.addReport(toReturn);
        return toReturn;

    }
    public Report showFaultyItems(ArrayList<Category> categories){
        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                List<SpecificItem> items = value.getSpecificItems();
                for(SpecificItem item : items)
                {
                    if (item.isFaulty())
                    {
                        itemsInReport.add(value);
                        break;
                    }
                }
            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        reportsMapper.addReport(toReturn);
        return toReturn;

    }
    // sales weekly report - by category/ies
    public Report getWeeklyReport(ArrayList<Category> categories){

        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                itemsInReport.add(value);
             }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now().minusWeeks(1),LocalDate.now());
        reportsMapper.addReport(toReturn);
        return toReturn;

        }


    public Sale addSale(Item item,int quantity){
        Sale sale = new Sale(item.getId(),item.getName(),item.getSellingPrice(),LocalDate.now(),quantity);
        salesMapper.addSale(sale);
        return sale;
    }

    public Report showMinAmountItems(ArrayList<Category> categories) {
        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                if (value.isMinAmount())
                    itemsInReport.add(value);
            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        reportsMapper.addReport(toReturn);
        return toReturn;

    }

    public SaleReport getSalesReport(ArrayList<Category> categories, LocalDate startDate, LocalDate endDate) {
        ArrayList<Sale> salesInReport = new ArrayList();
        for (Category c: categories) {
            for(Sale sale : salesMapper.getAllSales())
            {
                if (c.containsItem(sale.getItemID()))
                    if (sale.getSaleDate().compareTo(startDate) >= 0)
                        if (sale.getSaleDate().compareTo(endDate) <= 0) {
                            salesInReport.add(sale);
                        }
            }

        }
        SaleReport toReturn = new SaleReport(salesInReport,startDate,endDate);
        toReturn.setStartDate(startDate);
        toReturn.setEndDate(endDate);
        saleReportsMapper.addReport(toReturn);
        return toReturn;
    }

    public ArrayList<Sale> getSales(ArrayList<Integer> categories) {
        ArrayList<Sale> salesList = new ArrayList<>(salesMapper.getAllSales());
        return salesList;
    }

}
