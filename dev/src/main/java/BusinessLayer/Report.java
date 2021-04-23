package BusinessLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Report {

    private int reportID;
    private List<Item> items;
    private LocalDate startDate;
    private LocalDate endDate;

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
