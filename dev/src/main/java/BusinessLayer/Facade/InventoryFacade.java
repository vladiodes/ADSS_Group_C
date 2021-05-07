package BusinessLayer.Facade;

import BusinessLayer.Facade.Response;
import BusinessLayer.InventoryModule.*;
import DTO.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class InventoryFacade {
    private static InventoryFacade inventoryFacadeInstance = null;
    // singleton
    private RecordController recordController;
    private StockController stockController;

    private InventoryFacade(){
        this.recordController= RecordController.getInstance();
        this.stockController= StockController.getInstance();
    }
    public static InventoryFacade getInstance()
    {
        if(inventoryFacadeInstance == null)
            inventoryFacadeInstance = new InventoryFacade();
        return inventoryFacadeInstance;
    }

    public Response<Boolean> addSpecificItem(int generalItemID,int location,  int storageAmount, int shelfAmount, LocalDate expDate, String producer)
    {
        try
        {
            Boolean res = stockController.addSpecificItem(generalItemID,location,storageAmount,shelfAmount,expDate,producer);
            return new Response<>(res);
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
    public Response<ItemDTO> addItem(int categoryID, int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, double buyingPrice, double sellingPrice) {
        try {
            Item item = stockController.addItem(location, name, producer, storageAmount, shelfAmount, minAmount, expDate, categoryID, buyingPrice, sellingPrice);
            return new Response<>(new ItemDTO(item));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }
    }
    public Response<Boolean> removeFaultyItems()
    {
        try
        {
            stockController.removeFaultyItems();
            return new Response<>(true);
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
    public Response<Boolean> updateItem(int itemID, String name,  int minAmount,  double buyingPrice, double sellingPrice){
        try {
            stockController.updateItem(itemID,name,minAmount,buyingPrice,sellingPrice);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }

    }

    public Response<Boolean> updateCategory(int categoryID, String categoryName){
        try {
            stockController.updateCategory(categoryID,categoryName);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }
    public Response<Boolean> addCategoryDiscount(int categoryID, double discount){
        try {
            stockController.addCategoryDiscount(categoryID,discount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<Boolean> addItemDiscount(int itemID, double discount){
        try {
            stockController.addItemDiscount(itemID, discount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<CategoryDTO> addCategory(String name, int fatherID){
        try {
            Category c = stockController.addCategory(name, fatherID);
            return new Response<>(new CategoryDTO(c));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<ItemDTO> getItemByLocation(int location){
        try {
            Item item = stockController.getItemByLocation(location);
            return new Response<>(new ItemDTO(item));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }

    }

    public Response<Boolean> changeAlertTime(int itemID, int daysAmount){
        try {
            stockController.changeAlertTime(itemID,daysAmount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }
    // record controller
    public Response<defactReportDTO> showExpItems(){
        try
        {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showExpItems(categories);
            return new Response<>(new defactReportDTO(report));
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
    public Response<defactReportDTO> showFaultyItems() {
        try {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showFaultyItems(categories);
            return new Response<>(new defactReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }

    }
    // report by categories
    public Response<amountReportDTO> getWeeklyReport(ArrayList<Integer> categoriesList) {
        ArrayList<Category> categories = this.stockController.getCategories(categoriesList);
        try {
            Report report = recordController.getWeeklyReport(categories);
            return new Response<>(new amountReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }
    }

    public Response<SaleDTO> addSale(int itemID, int quantity){
         try {
             Item item = this.stockController.getItemById(itemID);
             this.stockController.sellItem(itemID,quantity);
             Sale sale = recordController.addSale(item,quantity);
             return new Response<>(new SaleDTO(sale));
         }
         catch (IllegalArgumentException e){
             return new Response<>(e);
         }
    }


    public Response<amountReportDTO> showMinAmountItems() {
        try {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showMinAmountItems(categories);
            return new Response<>(new amountReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }

    }
    public Response<Boolean> deleteItem(int itemID)
    {
        try
        {
            this.stockController.deleteItem(itemID);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }

    public Response<SaleReportDTO> showSalesReport(LocalDate startDate, LocalDate endDate, ArrayList<Integer> categoriesList) {
        ArrayList<Category> categories = this.stockController.getCategories(categoriesList);
        try {
            SaleReport report = recordController.getSalesReport(categories,startDate,endDate);
            return new Response<SaleReportDTO>(new SaleReportDTO(report));
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
}
