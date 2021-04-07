package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;
import DTO.ReportDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    // singleton
    private RecordController recordController;
    private StockController stockController;

     public Response<ItemDTO> addItem(int categoryID,int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,double buyingPrice) {
         try {
             Item item = stockController.addItem(location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,categoryID,buyingPrice);
             return new Response<ItemDTO>(new ItemDTO(item));
         } catch (IllegalArgumentException e) {
             return new Response<>(e);
         }
     }

    public Response<Boolean> updateItem(ItemDTO itemdto){
        try {
            stockController.updateItem(itemdto);

            return new Response<>(true);
        }
        catch (IllegalArgumentException e){
            return new Response<>(e);
        }

    }

    public Response<Boolean> updateCategory(CategoryDTO categoryDTO){
        try {
            stockController.updateCategory(categoryDTO);
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
    public Response<List<ItemDTO>> showExpItems(){
        try
        {
            List<Item> items = recordController.showExpItems();
            List<ItemDTO> toReturn = new ArrayList<>();
            for(Item i: items){
                toReturn.add((new ItemDTO(i)));
            }
            return new Response<>(toReturn);
        }
        catch (IllegalArgumentException e)
        {
            return new Response<>(e);
        }
    }
    public Response<ReportDTO> showFaultyItems() {
        try {
            Report report = recordController.showFaultyItems();
            return new Response<>(new ReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }

    }

    public Response<ReportDTO> getWeeklyReport(ArrayList<Integer> categories) {
        try {
            Report report = recordController.getWeeklyReport(categories);
            return new Response<>(new ReportDTO(report));
        } catch (IllegalArgumentException e) {
            return new Response<>(e);
        }
    }


}
