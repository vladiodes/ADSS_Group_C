package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.InventoryModule.Report;
import DTO.ReportDTO;
import DataAccessLayer.ReportDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportMapper {
    private static ReportMapper instance = null;
    private HashMap<Integer, Report> reportMapper;
    private ReportDAO dao;

    private ReportMapper() {
        reportMapper = new HashMap<>();
        dao = new ReportDAO();
    }

    public static ReportMapper getInstance() {
        if (instance == null)
            instance = new ReportMapper();
        return instance;
    }

    public Report buildReport(ReportDTO dto) {
        ArrayList<Item> itemsInReport = new ArrayList<>();
        for (int id : dto.itemsIDS) {
            Item i = ItemsMapper.getInstance().getItem(id);
            if (i != null)
                itemsInReport.add(i);
        }
        Report report = new Report(dto.reportID, itemsInReport, dto.startDate, dto.endDate);
        reportMapper.put(report.getReportID(), report);
        return report;
    }

    public int addReport(Report report){
        int id=dao.insert(new ReportDTO(report));
        reportMapper.put(id,report);
        return id;
    }

    public void updateReport(Report report){
        dao.update(new ReportDTO(report));
    }

    public Report getReport(int reportID){
        if(reportMapper.containsKey(reportID))
            return reportMapper.get(reportID);
        ReportDTO dto=dao.get(reportID);
        if(dto==null)
            throw new IllegalArgumentException("No such report in the system");
        return buildReport(dto);
    }
}
