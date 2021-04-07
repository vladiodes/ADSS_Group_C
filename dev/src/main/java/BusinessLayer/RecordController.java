package BusinessLayer;

import DTO.ItemDTO;
import DTO.ReportDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordController {
    private HashMap<Integer,Sale> sales;
    private HashMap<Integer,Report> reports;
    private HashMap<Integer,Item> faultyItems;


    // fields and constructor

    // get business object and return Response of DTO

    public List<Item> showExpItems(){
        return this.recordController.showExpItems();
    }
    // think about reportDTO
    public Report showFaultyItems(){
        return this.recordController.showFaultyItems();
    }
    //
    public Report getWeeklyReport(ArrayList<Integer> categories){
        return this.recordController.getWeeklyReport(categories);
    }

    public void addSale(int itemID, double buyingPrice, double sellingPrice, LocalDateTime saleDate){

    }

}
