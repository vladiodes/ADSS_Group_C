package BusinessLayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Report {

    private int reportID;
    private List<Item> items;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Report(int reportID){
        this.reportID=reportID;
        this.items=new ArrayList<>();
    }

    public int getReportID() {
        return reportID;
    }

    public List<Item> getItems() {
        return items;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
