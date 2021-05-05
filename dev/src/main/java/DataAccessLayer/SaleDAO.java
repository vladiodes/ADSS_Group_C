package DataAccessLayer;

import DTO.ItemDTO;
import DTO.SaleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleDAO extends DAO<SaleDTO> {
    private String QuantityCol="Quantity",itemCol="ItemID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s) VALUES(?,?)",tableName,QuantityCol,itemCol),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,QuantityCol,itemCol);

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
            ps.setInt(3, dto.id);
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
            rs.last();
            if (rs.getRow() == 0)
                return null;
            rs.beforeFirst();
            ItemDTO itemInSale=new ItemDAO().get(DAO.idCol,rs.getInt(itemCol));
            output = new SaleDTO(rs.getInt("ID"),rs.getInt(itemCol),itemInSale.getName(),itemInSale.getSellingPrice(),itemInSale.getExpDate(),rs.getInt(QuantityCol));
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

    public int delete(SaleDTO dto) {
        return delete("ID",String.valueOf(dto.id));
    }
}
