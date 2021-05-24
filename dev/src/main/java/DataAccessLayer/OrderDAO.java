package DataAccessLayer;

import BusinessLayer.SuppliersModule.Order;
import DTO.OrderDTO;
import Misc.Pair;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class OrderDAO extends DAO<OrderDTO> {
    String IDCol = "ID", DateOfOrderCol = "DateOfOrder", ShipmentStatusCol = "ShipmentStatus", PriceBeforeDiscountCol = "PriceBeforeDiscount",
            SupplierIDCol = "SupplierID", PriceAfterDiscountCol = "PriceAfterDiscount", IsFixedCol = "isFixed",
            INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?)", tableName, DateOfOrderCol, ShipmentStatusCol, PriceBeforeDiscountCol, SupplierIDCol, PriceAfterDiscountCol, IsFixedCol),
            UPDATE_SQL = String.format("Update %s SET %s=?, %s=? ,%s=? WHERE ID=?", tableName, ShipmentStatusCol, PriceBeforeDiscountCol, PriceAfterDiscountCol, IDCol);

    public OrderDAO() {
        super("Orders");
    }

    @Override
    public int insert(OrderDTO dto) {
        int id = -1;
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1, dto.dateOfOrder.format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(2, String.valueOf(dto.shipmentStatus));
            ps.setDouble(3, dto.priceBeforeDiscount);
            ps.setString(4, String.valueOf(dto.supplierID));
            ps.setDouble(5, dto.priceAfterDiscount);
            ps.setString(6, String.valueOf(dto.isFixed));
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
    public int update(OrderDTO dto) {
        int rowsAffected = -1;
        Connection con = Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1, String.valueOf(dto.shipmentStatus));
            ps.setString(2, String.valueOf(dto.priceBeforeDiscount));
            ps.setString(3, String.valueOf(dto.priceAfterDiscount));
            ps.setString(4, String.valueOf(dto.orderID));
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public OrderDTO get(int id) {
        OrderDTO output = null;
        HashMap<Integer, Pair<Integer, Integer>> productsInOrderIDs = new HashMap<>();
        int quantity = 0;
        Connection con = Repository.getInstance().connect();
        ResultSet rs = null;
        ResultSet products = null;
        Statement stmt = null;
        try {
            rs = get("ID", String.valueOf(id), con);
            if (rs.isClosed()) {
                Repository.getInstance().closeConnection(con);
                return null;
            }
            String query = String.format("SELECT ItemID , CatalogueID , Quantity FROM ProductsInOrder WHERE OrderID=%s", id);
            stmt = con.createStatement();
            products = stmt.executeQuery(query);
            while (products.next()) {
                productsInOrderIDs.put(products.getInt("ItemID"), new Pair<>(products.getInt("CatalogueID"), products.getInt("Quantity")));
                quantity += products.getInt(2);
            }
            output = new OrderDTO(LocalDate.parse(rs.getString(DateOfOrderCol)), rs.getInt(DAO.idCol), Order.ShipmentStatus.valueOf(rs.getString(ShipmentStatusCol)),
                    rs.getDouble(PriceBeforeDiscountCol), rs.getDouble(PriceAfterDiscountCol), quantity, productsInOrderIDs, rs.getString(IsFixedCol), rs.getInt(SupplierIDCol));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(OrderDTO dto) {
        return delete("ID", String.valueOf(dto.orderID));
    }

    public int addItemToOrder(OrderDTO orderDTO, int quantity, double totalPrice, int catalogueIDBySupplier, int supplierID, int id) {
        return executeQuery(String.format("INSERT INTO ProductsInOrder (OrderID,Quantity,TotalPrice,CatalogueID,SupplierID,ItemID)\n" +
                "VALUES (%s,%s,%s,%s,%s,%s);", orderDTO.orderID, quantity, totalPrice, catalogueIDBySupplier, supplierID, id));
    }

    public int updateItemInOrder(OrderDTO orderDTO, int quantity, double totalPrice, int catalogueIDBySupplier, int supplierID, int id) {
        return executeQuery(String.format("UPDATE ProductsInOrder Set Quantity=%s, TotalPrice=%s\n" +
                        "WHERE OrderID=%s AND CatalogueID=%s AND SupplierID=%s AND ItemID=%s", quantity, totalPrice, orderDTO.orderID, catalogueIDBySupplier,
                supplierID, id));
    }

    public int removeItemFromOrder(OrderDTO orderDTO, int catalogueIDBySupplier, int supplierID, int id) {
        return executeQuery(String.format("DELETE FROM ProductsInOrder WHERE OrderID=%s AND CatalogueID=%s AND SupplierID=%s AND ItemID=%s",
                orderDTO.orderID, catalogueIDBySupplier, supplierID, id));
    }
}
