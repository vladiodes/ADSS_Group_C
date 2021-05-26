package DataAccessLayer;

import DTO.ReportDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ReportDAO extends DAOV1<ReportDTO> {
    private String StartDate="StartDate",EndDate="EndDate",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s) VALUES(?,?)",tableName,StartDate,EndDate),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,StartDate,EndDate);

    public ReportDAO(){
        super("Report");
    }
    @Override
    public int insert(ReportDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,dto.startDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(2, dto.endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.executeUpdate();
            id=getInsertedID(con);
            for(int itemID:dto.itemsIDS) {
                PreparedStatement ps2=con.prepareStatement("INSERT into ItemsInReport (ItemID,ReportID) VALUES (?,?)");
                ps2.setInt(1,itemID);
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
    public int update(ReportDTO dto) {
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

    public ReportDTO get(int id){
        ReportDTO output=null;
        Connection con=Repository.getInstance().connect();
        ResultSet rs=get("ID",String.valueOf(id),con);
        try {
            rs.last();
            if (rs.getRow() == 0)
                return null;
            rs.beforeFirst();
            output = new ReportDTO(rs.getInt("ID"),rs.getDate(StartDate).toLocalDate(),rs.getDate(EndDate).toLocalDate(),Repository.getInstance().getIds(
                    "SELECT ItemID as ID from ItemsInReport\n" +
                            "WHERE ReportID="+id+";"
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
