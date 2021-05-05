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
    private int salesKey;
    private HashMap<Integer,Report> reports;
    private int reportsKey;
    private HashMap<Integer,Item> faultyItems;

    // -- constructor
    private RecordController() {
        this.sales=new HashMap<>();
        this.reports=new HashMap<>();
        this.reportsKey=1;
        this.salesKey=1;
        this.faultyItems=new HashMap<>();
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
        Report toReturn = new Report(reportsKey);
        toReturn.setItems(itemsInReport);
        toReturn.setStartDate(LocalDate.now());
        toReturn.setEndDate(LocalDate.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
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
        Report toReturn = new Report(reportsKey);
        toReturn.setItems(itemsInReport);
        toReturn.setStartDate(LocalDate.now());
        toReturn.setEndDate(LocalDate.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
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
        Report toReturn = new Report(reportsKey);
        toReturn.setItems(itemsInReport);
        // today, one week ago
        toReturn.setStartDate(LocalDate.now().minusWeeks(1));
        toReturn.setEndDate(LocalDate.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
        return toReturn;

        }


    public Sale addSale(Item item,int quantity){
        Sale sale = new Sale(this.salesKey,item.getId(),item.getName(),item.getSellingPrice(),LocalDate.now(),quantity);
        this.sales.put(salesKey,sale);
        this.salesKey++;
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
        Report toReturn = new Report(reportsKey);
        toReturn.setItems(itemsInReport);
        toReturn.setStartDate(LocalDate.now());
        toReturn.setEndDate(LocalDate.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
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
        SaleReport toReturn = new SaleReport(reportsKey,salesInReport);
        toReturn.setStartDate(startDate);
        toReturn.setEndDate(endDate);
        reports.put(reportsKey,toReturn);
        reportsKey++;
        return toReturn;
    }

    public ArrayList<Sale> getSales(ArrayList<Integer> categories) {
        ArrayList<Sale> salesList = new ArrayList<>(sales.values());
        return salesList;
    }

    public void clear() {
        this.reportsKey=1;
        this.salesKey=1;
        this.sales=new HashMap<>();
        this.reports=new HashMap<>();
        this.faultyItems=new HashMap<>();
    }
}
