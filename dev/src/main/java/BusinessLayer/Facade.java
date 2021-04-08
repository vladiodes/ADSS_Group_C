package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;
import DTO.ReportDTO;
import DTO.SaleDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    private static Facade facadeInstance = null;
    // singleton
    private RecordController recordController;
    private StockController stockController;

    private Facade(){
        this.recordController= RecordController.getInstance();
        this.stockController= StockController.getInstance();
    }

    public static Facade getInstance()
    {
        if(facadeInstance == null)
            facadeInstance = new Facade();
        return facadeInstance;
    }

     public Response<ItemDTO> addItem(int categoryID,int location,String name, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice) {
         try {
             Item item = stockController.addItem(location,name,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,categoryID,buyingPrice);
             return new Response<ItemDTO>(new ItemDTO(item));
         } catch (IllegalArgumentException e) {
             return new Response<>(e);
         }
     }

    public Response<Boolean> updateItem(int itemID,String name, int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice){
        try {
            stockController.updateItem(itemID,name,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }

    }

    public Response<Boolean> updateCategory(int categoryID,String categoryName){
        try {
            stockController.updateCategory(categoryID,categoryName);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }
    public Response<Boolean> addCategoryDiscount(int categoryID,double discount){
        try {
            stockController.addCategoryDiscount(categoryID,discount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<Boolean> addItemDiscount(int itemID,double discount){
        try {
            stockController.addItemDiscount(itemID, discount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<CategoryDTO> addCategory(String name,int fatherID){
        try {
            Category c = stockController.addCategory(name, fatherID);
            return new Response<CategoryDTO>(new CategoryDTO(c));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }

    public Response<ItemDTO> getItemByLocation(int location){
        try {
            Item item = stockController.getItemByLocation(location);
            return new Response<ItemDTO>(new ItemDTO(item));
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }

    }

    public Response<Boolean> changeAlertTime(int itemID,int daysAmount){
        try {
            stockController.changeAlertTime(itemID,daysAmount);
            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }
    }
    // record controller
    public Response<ReportDTO> showExpItems(){
        try
        {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showExpItems(categories);
            return new Response<>(new ReportDTO(report));
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
    public Response<ReportDTO> showFaultyItems() {
        try {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showFaultyItems(categories);
            return new Response<>(new ReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }

    }
    // report by categories
    public Response<ReportDTO> getWeeklyReport(ArrayList<Integer> categoriesList) {
        ArrayList<Category> categories = this.stockController.getCategories(categoriesList);
        try {
            Report report = recordController.getWeeklyReport(categories);
            return new Response<>(new ReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }
    }

    public Response<SaleDTO> addSale(int itemID,double sellingPrice,LocalDateTime saleDate){
         try {
             Item item = this.stockController.getItemById(itemID);
             Sale sale = recordController.addSale(item,sellingPrice,saleDate);
             return new Response<>(new SaleDTO(sale));
         }
         catch (IllegalArgumentException e){
             return new Response<>(e);
         }
    }


    public Response<ReportDTO> showMinAmountItems() {
        try {
            ArrayList<Category> categories = this.stockController.getAllCategories();
            Report report = recordController.showMinAmountItems(categories);
            return new Response<>(new ReportDTO(report));
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
}
