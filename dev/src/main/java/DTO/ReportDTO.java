package DTO;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.InventoryModule.Report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDTO {
    //============database fields===========//
    public List<Integer> itemsIDS;
    //======================================//
    public List<ItemDTO> items;
    public int reportID;
    public LocalDate startDate;
    public LocalDate endDate;

    public ReportDTO(int reportID,LocalDate startDate,LocalDate endDate,List<Integer> itemsIDS){
        this.reportID=reportID;
        this.startDate=startDate;
        this.endDate=endDate;
        this.itemsIDS=itemsIDS;
    }
    public ReportDTO(Report report) {
        this.reportID = report.getReportID();
        this.startDate = report.getStartDate();
        this.endDate = report.getEndDate();
        this.items = new ArrayList<>();
        itemsIDS=new ArrayList<>();
        for(Item item : report.getItems())
        {
            this.items.add(new ItemDTO(item));
            itemsIDS.add(item.getId());
        }
    }


}
