package BusinessLayer;

import DTO.ItemDTO;
import DTO.ReportDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordController {
    // -- fields
    private HashMap<Integer,Sale> sales;
    private int salesKey;
    private HashMap<Integer,Report> reports;
    private int reportsKey;
    private HashMap<Integer,Item> faultyItems;

    // -- constructor
    public RecordController() {
        this.sales=new HashMap<>();
        this.reports=new HashMap<>();
        this.reportsKey=0;
        this.salesKey=0;
        this.faultyItems=new HashMap<>();
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
        toReturn.setStartDate(LocalDateTime.now());
        toReturn.setEndDate(LocalDateTime.now());
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
        toReturn.setStartDate(LocalDateTime.now());
        toReturn.setEndDate(LocalDateTime.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
        return toReturn;

    }
    // sales weekly report - by category/ies
    public Report getWeeklyReport(ArrayList<Category> categories){

        // maybe have to bring the items from sales

        List itemsInReport = new ArrayList();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                // if the sale is from the last week
                itemsInReport.add(value);
             }
            }
        Report toReturn = new Report(reportsKey);
        toReturn.setItems(itemsInReport);
        // today, one week ago
        toReturn.setStartDate(LocalDateTime.now().minusWeeks(1));
        toReturn.setEndDate(LocalDateTime.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
        return toReturn;

        }


    public Sale addSale(Item item, double sellingPrice, LocalDateTime saleDate){
        Sale sale = new Sale(this.salesKey,item.getId(),item.getBuyingPrice(),sellingPrice,saleDate);
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
        toReturn.setStartDate(LocalDateTime.now());
        toReturn.setEndDate(LocalDateTime.now());
        reports.put(reportsKey,toReturn);
        this.reportsKey++;
        return toReturn;

    }
}
