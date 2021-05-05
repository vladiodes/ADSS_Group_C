package DataAccessLayer;

import DTO.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ItemDAO extends DAO<ItemDTO> {
    private final String ExpirationCol = "ExpirationDate";
    private final String LocationCol = "Location";
    private final String NameCol = "Name";
    private final String minCol = "minAmmount";
    private final String availableCol = "availableAmmount";
    private final String ProducerCol = "Producer";
    private final String ShelfCol = "ShelfAmmount";
    private final String sellingPrice = "SellingPrice";
    private final String Storage = "StorageAmmount";
    private final String CategoryIDCOL = "CategoryID";
    //@TODO: add alert time column
    private final String INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?,?,?,?,?)", tableName, ExpirationCol, LocationCol,
            NameCol, minCol, availableCol, ProducerCol, ShelfCol, sellingPrice, Storage, CategoryIDCOL);

    private final String UPDATE_SQL = String.format("Update %s SET %s=?, %s=?, %s=?, %s=?, %s=?,%s=?, %s=?, %s=?, %s=?, %s=? WHERE ID=?",
            tableName, ExpirationCol, LocationCol,
            NameCol, minCol, availableCol, ProducerCol, ShelfCol, sellingPrice, Storage, CategoryIDCOL);

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
            ps.setInt(11, dto.getID());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public ItemDTO get(int id) {
        ItemDTO output = null;
        ResultSet rs = get("ID", String.valueOf(id));
        try {
            rs.last();
            if (rs.getRow() == 0)
                return null;
            rs.beforeFirst();
            output = new ItemDTO(rs.getInt("ID"), rs.getString(NameCol), rs.getInt(LocationCol), rs.getString(ProducerCol),
                    rs.getInt(availableCol), rs.getInt(Storage), rs.getInt(ShelfCol), rs.getInt(minCol), rs.getDate(ExpirationCol), rs.getDouble(sellingPrice), rs.getInt(CategoryIDCOL));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public int delete(ItemDTO dto) {
        return delete("ID", String.valueOf(dto.getID()));
    }
}
