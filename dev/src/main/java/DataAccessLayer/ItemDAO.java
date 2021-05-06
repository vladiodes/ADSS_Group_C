package DataAccessLayer;

import DTO.ItemDTO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemDAO extends DAO<ItemDTO> {
    public static final String ExpirationCol = "ExpirationDate";
    public static final String LocationCol = "Location";
    public static final String NameCol = "Name";
    public static final String minCol = "minAmmount";
    public static final String availableCol = "availableAmmount";
    public static final String ProducerCol = "Producer";
    public static final String ShelfCol = "ShelfAmmount";
    public static final String sellingPrice = "SellingPrice";
    public static final String Storage = "StorageAmmount";
    public static final String CategoryIDCOL = "CategoryID";
    public static final String alertCol="Alert";
    private final String INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?,?,?,?,?,?)", tableName, ExpirationCol, LocationCol,
            NameCol, minCol, availableCol, ProducerCol, ShelfCol, sellingPrice, Storage, CategoryIDCOL,alertCol);

    private final String UPDATE_SQL = String.format("Update %s SET %s=?, %s=?, %s=?, %s=?, %s=?,%s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE ID=?",
            tableName, ExpirationCol, LocationCol,
            NameCol, minCol, availableCol, ProducerCol, ShelfCol, sellingPrice, Storage, CategoryIDCOL,alertCol);

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
            ps.setString(1, dto.getExpDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setInt(2, dto.getLocation());
            ps.setString(3, dto.getName());
            ps.setInt(4, dto.getMinAmount());
            ps.setInt(5, dto.getAvailableAmount());
            ps.setString(6, dto.getProducer());
            ps.setInt(7, dto.getShelfAmount());
            ps.setDouble(8, dto.getSellingPrice());
            ps.setInt(9, dto.getStorageAmount());
            ps.setInt(10, dto.getCategoryID());
            ps.setInt(11,dto.getAlertTime());
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
            ps.setString(1, dto.getExpDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(2, String.valueOf(dto.getLocation()));
            ps.setString(3, dto.getName());
            ps.setInt(4, dto.getMinAmount());
            ps.setInt(5, dto.getAvailableAmount());
            ps.setString(6, dto.getProducer());
            ps.setInt(7, dto.getShelfAmount());
            ps.setDouble(8, dto.getSellingPrice());
            ps.setInt(9, dto.getStorageAmount());
            ps.setInt(10, dto.getCategoryID());
            ps.setInt(11,dto.getAlertTime());
            ps.setInt(12, dto.getID());
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
                        rs.getInt(availableCol), rs.getInt(Storage), rs.getInt(ShelfCol), rs.getInt(minCol), LocalDate.parse(rs.getString(ExpirationCol)), rs.getDouble(sellingPrice), rs.getInt(CategoryIDCOL),rs.getInt(alertCol));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(ItemDTO dto) {
        return delete(DAO.idCol, String.valueOf(dto.getID()));
    }
}
