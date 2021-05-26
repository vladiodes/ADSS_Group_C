package DataAccessLayer;

import DTO.ItemDTO;
import DTO.SaleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO extends DAOV1<SaleDTO> {
    private String QuantityCol="Quantity",itemCol="ItemID",SaleDateCol="SaleDate",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s) VALUES(?,?,?)",tableName,QuantityCol,itemCol,SaleDateCol),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=?,%s=? WHERE ID=?",tableName,QuantityCol,itemCol,SaleDateCol);

    public SaleDAO(){
        super("Sale");
    }

    @Override
    public int insert(SaleDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setInt(1,dto.quantity);
            ps.setInt(2,dto.itemID);
            ps.setString(3,dto.saleDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.executeUpdate();
            id=getInsertedID(con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }

    @Override
    public int update(SaleDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setInt(1,dto.quantity);
            ps.setInt(2, dto.itemID);
            ps.setString(3,dto.saleDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setInt(4, dto.id);
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public SaleDTO get(int id) {
        SaleDTO output=null;
        Connection con=Repository.getInstance().connect();
        ResultSet rs=get("ID",String.valueOf(id),con);
        try {
            if (!rs.isClosed()) {
                ItemDTO itemInSale = new ItemDAO().get(DAOV1.idCol, rs.getInt(itemCol));
                output = new SaleDTO(rs.getInt("ID"), rs.getInt(itemCol), itemInSale.getName(), itemInSale.getSellingPrice(), LocalDate.parse(rs.getString(SaleDateCol)), rs.getInt(QuantityCol));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        if(output==null)
            return null;
        return output;
    }

    public List<SaleDTO> getAllSales(){
        ArrayList<SaleDTO> output=new ArrayList<>();
        Connection con=Repository.getInstance().connect();
        ResultSet rs=getAll(con);
        try {
            while (rs.next()){
                ItemDTO itemInSale = new ItemDAO().get(DAOV1.idCol, rs.getInt(itemCol));
                output.add(new SaleDTO(rs.getInt("ID"), rs.getInt(itemCol), itemInSale.getName(), itemInSale.getSellingPrice(), LocalDate.parse(rs.getString(SaleDateCol)), rs.getInt(QuantityCol)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(SaleDTO dto) {
        return delete("ID",String.valueOf(dto.id));
    }
}
