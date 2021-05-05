package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.ReportMapper;

import java.time.LocalDate;
import java.util.List;

public class Report {

    private int reportID;
    private List<Item> items;
    private LocalDate startDate;
    private LocalDate endDate;

    public Report(int id){
        reportID=id;
    }
    public Report(List<Item> items,LocalDate startDate,LocalDate endDate){
        this.items=items;
        this.startDate=startDate;
        this.endDate=endDate;
        reportID=ReportMapper.getInstance().addReport(this);
    }
    public Report(int reportID,List<Item> items,LocalDate startDate,LocalDate endDate){
        this.items=items;
        this.startDate=startDate;
        this.endDate=endDate;
        this.reportID=reportID;
    }

    public int getReportID() {
        return reportID;
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
