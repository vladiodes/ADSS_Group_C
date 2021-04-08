package DTO;

import BusinessLayer.Item;
import BusinessLayer.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportDTO {
    private List<String> items;
    private int reportID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ReportDTO(Report report) {
        this.reportID = report.getReportID();
        this.startDate = report.getStartDate();
        this.endDate = report.getEndDate();
        this.items = new ArrayList<>();
        for(Item item : report.getItems())
        {
            this.items.add((item.getName()));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Report ID: "+ this.reportID + "\n");
        builder.append("Report Start Date: "+ this.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report End Date: "+ this.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report Items:\n");
        for(String itemName : items){
            builder.append(itemName +" ");
        }
        return builder.toString();
    }
}
