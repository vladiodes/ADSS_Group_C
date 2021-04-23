package DTO;

import BusinessLayer.Report;

import java.time.format.DateTimeFormatter;

public class defactReportDTO extends ReportDTO {
    public defactReportDTO(Report report) {
        super(report);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Report ID: "+ this.reportID + "\n");
        builder.append("Report Start Date: "+ this.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report End Date: "+ this.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n");
        builder.append("Report Items:\n");
        for(ItemDTO item : items){
            builder.append("item id :"+item.getID() +" ");
            builder.append("item name : " +item.getName() +" \n");
        }
        return builder.toString();
    }
}
