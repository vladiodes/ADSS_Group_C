package BusinessLayer.DTO;

import java.time.LocalDateTime;

public class Order {
    public LocalDateTime dateOfOrder;
    public int orderID;
    public BusinessLayer.Order.ShipmentStatus shipmentStatus;
    public double priceBeforeDiscount;
    public int totalQuantity;
    public boolean isFixed;

    public Order(BusinessLayer.Order order){
        dateOfOrder=order.getDateOfOrder();
        orderID=order.getOrderID();
        shipmentStatus=order.getShipmentStatus();
        priceBeforeDiscount=order.getPriceBeforeDiscount();
        totalQuantity=order.getTotalQuantity();
        isFixed=order.getisFixed();
    }
}
