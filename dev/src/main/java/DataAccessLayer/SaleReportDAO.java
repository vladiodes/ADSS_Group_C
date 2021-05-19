package DataAccessLayer;

import DTO.ReportDTO;
import DTO.SaleDTO;
import DTO.SaleReportDTO;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class SaleReportDAO extends DAO<SaleReportDTO> {
    private String StartDate="StartDate",EndDate="EndDate",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s) VALUES(?,?)",tableName,StartDate,EndDate),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,StartDate,EndDate);

    public SaleReportDAO(){
        super("SaleReport");
    }

    @Override
    public int insert(SaleReportDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,dto.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(2, dto.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.executeUpdate();
            id=getInsertedID(con);
            for(int saleId:dto.saleIDS) {
                PreparedStatement ps2=con.prepareStatement("INSERT into SalesInReport (SaleID,SaleReportID) VALUES (?,?)");
                ps2.setInt(1,saleId);
                ps2.setInt(2,id);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }

    @Override
    public int update(SaleReportDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1,dto.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(2, dto.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setInt(3, dto.reportID);
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public SaleReportDTO get(int id){
        SaleReportDTO output=null;
        Connection con=Repository.getInstance().connect();
        ResultSet rs=get("ID",String.valueOf(id),con);
        try {
            rs.last();
            if (rs.getRow() == 0)
                return null;
            rs.beforeFirst();
            output = new SaleReportDTO(rs.getInt("ID"),rs.getDate(StartDate).toLocalDate(),rs.getDate(EndDate).toLocalDate(),Repository.getInstance().getIds(
                    "SELECT SaleID as ID\n" +
                            "FROM SalesInReport\n" +
                            "WHERE SaleReportID="+id+";"
            ));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }
}
