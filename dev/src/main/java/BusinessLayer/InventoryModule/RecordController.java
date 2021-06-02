package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.SaleMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordController {
    // -- fields
    private static RecordController recordControllerInstance = null;
    private SaleMapper salesMapper;

    // -- constructor
    private RecordController() {
        salesMapper=SaleMapper.getInstance();
    }

    public static RecordController getInstance(){
        if(recordControllerInstance == null)
            recordControllerInstance = new RecordController();
        return recordControllerInstance;
    }


    // if one of the specific items of the Item is expired we will add the item to the report
    public Report showExpItems(ArrayList<Category> categories){
        List<Item> itemsInReport = new ArrayList<>();
        for (Category c: categories) {
            for (Item item : c.getItems().values()) {
                List<SpecificItem> items = item.getSpecificItems();
                for(SpecificItem specificItem : items)
                {
                    if (specificItem.isExp(item.getAlertTime()) & !specificItem.isFaulty())
                    {
                        itemsInReport.add(item);
                        break;
                    }
                }

            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        return toReturn;

    }
    public Report showFaultyItems(ArrayList<Category> categories){
        List<Item> itemsInReport = new ArrayList<>();
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
        return toReturn;

    }
    // sales weekly report - by category/ies
    public Report getWeeklyReport(ArrayList<Category> categories){

        List<Item> itemsInReport = new ArrayList<>();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                itemsInReport.add(value);
             }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now().minusWeeks(1),LocalDate.now());
        return toReturn;
        }


    public Sale addSale(Item item,int quantity){
        Sale sale = new Sale(item.getId(),item.getName(),item.getSellingPrice(),LocalDate.now(),quantity);
        salesMapper.addSale(sale);
        return sale;
    }

    public Report showMinAmountItems(ArrayList<Category> categories) {
        List<Item> itemsInReport = new ArrayList<>();
        for (Category c: categories) {
            for (Map.Entry<Integer, Item> entry : c.getItems().entrySet()) {
                Item value = entry.getValue();
                if (value.isMinAmount())
                    itemsInReport.add(value);
            }
        }
        Report toReturn = new Report(itemsInReport,LocalDate.now(),LocalDate.now());
        return toReturn;

    }

    public SaleReport getSalesReport(ArrayList<Category> categories, LocalDate startDate, LocalDate endDate) {
        ArrayList<Sale> salesInReport = new ArrayList<>();
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
        return toReturn;
    }

    public ArrayList<Sale> getSales(ArrayList<Integer> categories) {
        ArrayList<Sale> salesList = new ArrayList<>();
        for (Category category : StockController.getInstance().getCategories(categories)) {
            for (Sale sale : salesMapper.getAllSales()) {
                if (category.containsItem(sale.getItemID()))
                    salesList.add(sale);
            }
        }
        return salesList;
    }

}
