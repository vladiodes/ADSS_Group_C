package DataAccessLayer;

import DTO.ItemDTO;
import DTO.specificItemDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO<ItemDTO> {
    public static final String LocationCol = "Location";
    public static final String NameCol = "Name";
    public static final String minCol = "minAmmount";
    public static final String ProducerCol = "Producer";
    public static final String sellingPrice = "SellingPrice";
    public static final String CategoryIDCOL = "CategoryID";
    public static final String alertCol="Alert";
    private final String INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?,?)", tableName,LocationCol,NameCol,minCol,ProducerCol,sellingPrice,CategoryIDCOL,alertCol);

    private final String UPDATE_SQL = String.format("Update %s SET %s=?, %s=?, %s=?, %s=?, %s=?,%s=?, %s=? WHERE ID=?",
            tableName, LocationCol,
            NameCol, minCol, ProducerCol, sellingPrice, CategoryIDCOL,alertCol);

    public ItemDAO() {
        super("Item");
    }

    @Override
    public int insert(ItemDTO dto) {
        int id = -1;
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setInt(1, dto.getLocation());
            ps.setString(2, dto.getName());
            ps.setInt(3, dto.getMinAmount());
            ps.setString(4, dto.getProducer());
            ps.setDouble(5, dto.getSellingPrice());
            ps.setInt(6, dto.getCategoryID());
            ps.setInt(7, dto.getAlertTime());
            ps.executeUpdate();
            id = getInsertedID(con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }

    @Override
    public int update(ItemDTO dto) {
        int rowsAffected = -1;
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1, String.valueOf(dto.getLocation()));
            ps.setString(2, dto.getName());
            ps.setInt(3, dto.getMinAmount());
            ps.setString(4, dto.getProducer());
            ps.setDouble(5, dto.getSellingPrice());
            ps.setInt(6, dto.getCategoryID());
            ps.setInt(7, dto.getAlertTime());
            ps.setInt(8,dto.getID());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public ItemDTO get(String column,int id) {
        ItemDTO output = null;
        Connection con=Repository.getInstance().connect();
        ResultSet rs = get(column, String.valueOf(id),con);
        try {
            if (!rs.isClosed())
                output = new ItemDTO(rs.getInt("ID"), rs.getString(NameCol), rs.getInt(LocationCol), rs.getString(ProducerCol),
                         rs.getInt(minCol), rs.getDouble(sellingPrice), rs.getInt(CategoryIDCOL),rs.getInt(alertCol));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        if(output!=null)
            output.setSpecificItemDTOList(getSpecificItems(output.getID()));
        return output;
    }

    public int delete(ItemDTO dto) {
        return delete(DAO.idCol, String.valueOf(dto.getID()));
    }

    public List<specificItemDTO> getSpecificItems(int itemID){
        Connection con=Repository.getInstance().connect();
        ResultSet rs=super.selectQuerry(con,"SELECT * from SpecificItems\n" +
                "where ItemID=" + itemID + ";");
        ArrayList<specificItemDTO> output=new ArrayList<>();
        try {
            while (rs.next())
                output.add(new specificItemDTO(rs.getInt("ID"), LocalDate.parse(rs.getString("expDate")),rs.getInt("storageAmount"),rs.getInt("shelfAmount"),itemID));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);

        }
        return output;
    }

    public int insertSpecificItem(specificItemDTO specificItemDTO) {
        int id = -1;
        String query="Insert INTO SpecificItems (expDate,storageAmount,shelfAmount,itemID)\n" +
                "VALUES (?,?,?,?)";
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, specificItemDTO.expDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setInt(2, specificItemDTO.storageAmount);
            ps.setInt(3, specificItemDTO.shelfAmount);
            ps.setInt(4, specificItemDTO.generalID);
            ps.executeUpdate();
            this.tableName="SpecificItems";
            id = getInsertedID(con);
            this.tableName="Item";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }

    public int deleteSpecific(specificItemDTO specificItemDTO) {
        return executeQuery("DELETE FROM SpecificItems\n" +
                "WHERE ID="+specificItemDTO.id+";");
    }

    public int updateSpecificItem(specificItemDTO specificItemDTO) {
        int rowsAffected = -1;
        String query=String.format("UPDATE SpecificItems SET\n" +
                "expDate=?, storageAmount=?, shelfAmount=? WHERE ID=%s;",specificItemDTO.id);
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, specificItemDTO.expDate.toString());
            ps.setInt(2, specificItemDTO.storageAmount);
            ps.setInt(3, specificItemDTO.shelfAmount);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }
}
