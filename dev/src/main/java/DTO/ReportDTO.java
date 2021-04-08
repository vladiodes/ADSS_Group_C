package DTO;

import BusinessLayer.Item;
import BusinessLayer.Report;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDTO {
    private List<String> items;
    private int reportID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ReportDTO(Report report) {

    }
}
