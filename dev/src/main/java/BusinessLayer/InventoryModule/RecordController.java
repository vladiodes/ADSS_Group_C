package BusinessLayer.InventoryModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordController {
    // -- fields
    private static RecordController recordControllerInstance = null;
    private HashMap<Integer,Sale> sales;
    private HashMap<Integer,Report> reports;
    private HashMap<Integer,SaleReport> saleReports;
    private HashMap<Integer,Item> faultyItems;

    // -- constructor
    private RecordController() {
        this.sales=new HashMap<>();
        this.reports=new HashMap<>();
        this.faultyItems=new HashMap<>();
        saleReports=new HashMap<>();
    }

    public static RecordController getInstance(){
        if(recordControllerInstance == null)
            recordControllerInstance = new RecordController();
        return recordControllerInstance;
    }



    public Report showExpItems(ArrayList<Category> categories){
        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                if (value.isExp() & !value.isFaulty())
                    itemsInReport.add(value);
            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        reports.put(toReturn.getReportID(),toReturn);
        return toReturn;

    }
    public Report showFaultyItems(ArrayList<Category> categories){
        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                if (value.isFaulty())
                    itemsInReport.add(value);
            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());

        reports.put(toReturn.getReportID(),toReturn);
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
        reports.put(toReturn.getReportID(),toReturn);
        return toReturn;

        }


    public Sale addSale(Item item,int quantity){
        Sale sale = new Sale(item.getId(),item.getName(),item.getSellingPrice(),LocalDate.now(),quantity);
        this.sales.put(sale.getSaleID(),sale);
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
        reports.put(toReturn.getReportID(),toReturn);
        return toReturn;

    }

    public SaleReport getSalesReport(ArrayList<Category> categories, LocalDate startDate, LocalDate endDate) {
        ArrayList<Sale> salesInReport = new ArrayList();
        for (Category c: categories) {
            for(Sale sale : sales.values())
            {
                if(c.containsItem(sale.getItemID()) && sale.getSaleDate().compareTo(startDate) >=0 && sale.getSaleDate().compareTo(endDate) <=0){
                    salesInReport.add(sale);
                }
            }

        }
        SaleReport toReturn = new SaleReport(salesInReport,startDate,endDate);
        toReturn.setStartDate(startDate);
        toReturn.setEndDate(endDate);
        saleReports.put(toReturn.getReportID(),toReturn);
        return toReturn;
    }

    public ArrayList<Sale> getSales(ArrayList<Integer> categories) {
        ArrayList<Sale> salesList = new ArrayList<>(sales.values());
        return salesList;
    }

    public void clear() {
        this.sales=new HashMap<>();
        this.reports=new HashMap<>();
        this.faultyItems=new HashMap<>();
    }
}
