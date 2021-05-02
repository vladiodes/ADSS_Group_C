package DataAccessLayer;

import DTO.OrderDTO;

public class OrderDAO extends DAO<OrderDTO> {
    String IDCol="ID",DateOfOrderCol="DateOfOrder",ShipmentStatusCol="ShipmentStatus",PriceBeforeDiscountCol="PriceBeforeDiscount",
            SupplierIDCol="SupplierID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,DateOfOrderCol,ShipmentStatusCol,PriceBeforeDiscountCol,SupplierIDCol),
            UPDATE_SQL=String.format("Update %s SET %s=?, %s=? WHERE ID=?",tableName,ShipmentStatusCol,PriceBeforeDiscountCol);//check if order not received?
    public OrderDAO(){super("Order");}
    @Override
    public int insert(OrderDTO dto) {
        return 0;
    }

    @Override
    public int update(OrderDTO dto) {
        return 0;
    }
}
