package DataAccessLayer;

import BusinessLayer.SuppliersModule.Order;
import DTO.OrderDTO;
import DTO.SupplierDTO;
import javafx.util.Pair;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

public class OrderDAO extends DAO<OrderDTO> {
    String IDCol="ID",DateOfOrderCol="DateOfOrder",ShipmentStatusCol="ShipmentStatus",PriceBeforeDiscountCol="PriceBeforeDiscount",
            SupplierIDCol="SupplierID",PriceAfterDiscountCol="PriceAfterDiscount",IsFixedCol="isFixed",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?)",tableName,DateOfOrderCol,ShipmentStatusCol,PriceBeforeDiscountCol,SupplierIDCol,PriceAfterDiscountCol,IsFixedCol),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? ,%s=? WHERE ID=?",tableName,ShipmentStatusCol,PriceBeforeDiscountCol,PriceAfterDiscountCol,IDCol);
    public OrderDAO(){super("Orders");}
    @Override
    public int insert(OrderDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,String.valueOf(dto.dateOfOrder));
            ps.setString(2, String.valueOf(dto.shipmentStatus));
            ps.setString(3,String.valueOf(dto.priceBeforeDiscount));
            ps.setString(4,String.valueOf(dto.supplierID));
            ps.setString(5,String.valueOf(dto.priceAfterDiscount));
            ps.setString(6,String.valueOf(dto.isFixed));
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
    public int update(OrderDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1,String.valueOf(dto.shipmentStatus));
            ps.setString(2,String.valueOf(dto.priceBeforeDiscount));
            ps.setString(3,String.valueOf(dto.priceAfterDiscount));
            ps.setString(4,String.valueOf(dto.orderID));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public OrderDTO get(int id){
        OrderDTO output=null;
        HashMap<Integer, Pair<Integer,Integer>> productsInOrderIDs=new HashMap<>();
        int quantity=0;
        Connection con=Repository.getInstance().connect();
        ResultSet rs=null;
        ResultSet products=null;
        Statement stmt=null;
        try {
            rs=get("ID",String.valueOf(id),con);
            if(!rs.next()){
                Repository.getInstance().closeConnection(con);
                return null;
            }
            rs.first();
            String query=String.format("SELECT ItemID , CatalogueID , Quantity FROM ProductsInOrder WHERE OrderID=?",id);
            stmt=con.createStatement();
            products=stmt.executeQuery(query);
            while (products.next()){
                productsInOrderIDs.put(products.getInt(0),new Pair<>(products.getInt(1),products.getInt(2)));
                quantity+=products.getInt(2);
            }
            LocalDateTime orderdate=new java.sql.Timestamp(
                    rs.getDate(1).getTime()).toLocalDateTime();
            output=new OrderDTO(orderdate,id,rs.getInt(2),rs.getDouble(3),rs.getDouble(5),quantity,productsInOrderIDs,rs.getString(6),rs.getInt(4));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(OrderDTO dto) {
        return  delete("ID",String.valueOf(dto.orderID));
    }
}
