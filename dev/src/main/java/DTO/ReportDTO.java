package DTO;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.InventoryModule.Report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDTO {
    protected List<ItemDTO> items;
    protected int reportID;
    protected LocalDate startDate;
    protected LocalDate endDate;

    public ReportDTO(Report report) {
        this.reportID = report.getReportID();
        this.startDate = report.getStartDate();
        this.endDate = report.getEndDate();
        this.items = new ArrayList<>();
        for(Item item : report.getItems())
        {
            this.items.add(new ItemDTO(item));
        }
    }


}
