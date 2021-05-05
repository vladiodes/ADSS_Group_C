package DataAccessLayer;

import DTO.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO extends DAO<OrderDTO> {
    String IDCol="ID",DateOfOrderCol="DateOfOrder",ShipmentStatusCol="ShipmentStatus",PriceBeforeDiscountCol="PriceBeforeDiscount",
            SupplierIDCol="SupplierID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,DateOfOrderCol,ShipmentStatusCol,PriceBeforeDiscountCol,SupplierIDCol),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,ShipmentStatusCol,PriceBeforeDiscountCol,IDCol);
    public OrderDAO(){super("Order");}
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
            ps.setString(3,String.valueOf(dto.orderID));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public OrderDTO get(int id){
        //ResultSet rs=get("ID",String.valueOf(id));
        //@TODO: continue from here
        return null;
    }
}
