package DTO;

import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.SuppliersModule.ProductInOrder;
import javafx.util.Pair;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDTO {
    //For loading from database//
    public HashMap<Integer, Pair<Integer,Integer>> productIds;//each product in order (mapped by store id to a pair of (catalog id,quantity)

    public LocalDateTime dateOfOrder;
    public int orderID;
    public Order.ShipmentStatus shipmentStatus;
    public double priceBeforeDiscount;
    public double priceAfterDiscount;
    public int totalQuantity;
    public List<String> productsInOrder;
    public boolean isFixed;
    public int supplierID;

    public OrderDTO(Order order,int supplierID) {
        dateOfOrder = order.getDateOfOrder();
        orderID = order.getOrderID();
        this.supplierID=supplierID;
        shipmentStatus = order.getShipmentStatus();
        priceBeforeDiscount = order.getPriceBeforeDiscount();
        priceAfterDiscount = order.getPriceAfterDiscount();
        totalQuantity = order.getTotalQuantity();
        isFixed = order.getisFixed();
        productsInOrder = new ArrayList<>();
        for (ProductInOrder pio : order.getProductsInOrder())
            productsInOrder.add("\nProduct name: " + pio.getContract().getProduct().getName() +
                    "\nQuantity: " + pio.getQuantity() +
                    "\nTotal price after discounts: " + pio.getTotalPrice());
    }

    public OrderDTO(LocalDateTime dateOfOrder, int orderID, int shipmentStatus, double priceBeforeDiscount, double priceAfterDiscount, int totalQuantity,HashMap <Integer, Pair<Integer,Integer>> productsInOrderIDs, String isFixed, int supplierID) {
        this.dateOfOrder=dateOfOrder;
        this.orderID=orderID;
        if(shipmentStatus==0){
            this.shipmentStatus= Order.ShipmentStatus.WaitingForDelivery;
        }
        else {
            this.shipmentStatus= Order.ShipmentStatus.Delivered;
        }
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.priceAfterDiscount=priceAfterDiscount;
        this.totalQuantity=totalQuantity;
        this.productIds=productsInOrderIDs;
        if(isFixed.equals("True")){
            this.isFixed=true;
        }
        else {
            this.isFixed=false;
        }
        this.supplierID=supplierID;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Order id: " + orderID);
        builder.append("\nDate of order: " + dateOfOrder.format(DateTimeFormatter.ISO_LOCAL_DATE));
        builder.append("\nShippment status: " + shipmentStatus);
        builder.append("\nPrice before discounts: " + priceBeforeDiscount);
        builder.append("\nPrice after discounts: " + priceAfterDiscount);
        builder.append("\nTotal quantity of items: " + totalQuantity);
        builder.append("\nFixed order: " + isFixed);
        builder.append("\nProducts in the order:");
        for(String product:productsInOrder)
            builder.append("\n" + product);
        builder.append("\n");
        return builder.toString();
    }
}
