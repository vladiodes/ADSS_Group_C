package BusinessLayer;

import java.time.LocalDateTime;
import java.util.*;

public class Order{
    private LocalDateTime dateOfOrder;
    private int orderID;
    private ShipmentStatus shipmentStatus;
    private double priceBeforeDiscount;
    private int totalQuantity;
    private Set<ProductInOrder> productsInOrder;
    private boolean isFixed;

    public Order(LocalDateTime date,Boolean isFixed,int ID){
        setDateOfOrder(date);
        setOrderID(ID);
        shipmentStatus=ShipmentStatus.WaitingForDelivery;
        priceBeforeDiscount=0;
        totalQuantity=0;
        productsInOrder=new LinkedHashSet<>();
        setisFixed(isFixed);
    }

    //this constructor is used to reorder fixed orders. it receives a fixed order and an ID and date for the copy
    //of the fixed order and creates a copy.
    public Order(Order original,int ID,LocalDateTime date){
        setDateOfOrder(date);
        setOrderID(ID);
        shipmentStatus=ShipmentStatus.WaitingForDelivery;
        priceBeforeDiscount=original.priceBeforeDiscount;
        totalQuantity=original.totalQuantity;
        productsInOrder=original.productsInOrder;
        isFixed=true;
    }

    public boolean getisFixed(){
        return isFixed;
    }

    public int getOrderID(){
        return orderID;
    }

    //this function adds an item to an order. it first searches the productInOrder object matching to that
    //product.if there is no such object it adds a new entry to the products in order set .otherwise it will
    //update the already existing object using the orderMore method of productInOrder class.
    public void addItem(Product product,double pricePerUnit,int catalogueIDBySupplier,int quantity){
        ProductInOrder pio=findProductInOrder(product);
        if(pio==null){
            productsInOrder.add(new ProductInOrder(quantity,pricePerUnit*quantity,catalogueIDBySupplier,this,product));
        }
        else {
            pio.orderMore(quantity);
        }
        totalQuantity+=quantity;
        priceBeforeDiscount+=quantity*pricePerUnit;
    }

    //this functions receives a product and searches for a productInOrder matching to that product.
    //if one is found we return it. otherwise return null.
    public ProductInOrder findProductInOrder(Product product){
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getProduct().equals(product)){
                return pio;
            }
        }
        return null;
    }

    //this function is used when an order is received.simply change the status to received(true).
    public void receive() {
        shipmentStatus=ShipmentStatus.Delivered;
    }

    //this function tries to remove a product from the order.we search for a productInOrder object
    //that matches the product id given. if we found one we remove it and update the price before discount and total quantity
    //accordingly. otherwise, we throw an exception.
    public void removeProduct(int productID) {
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getProduct().getID()==productID)
                productsInOrder.remove(pio);
            totalQuantity-=pio.getQuantity();
            priceBeforeDiscount-=pio.getQuantity()*pio.getTotalPrice();
            return;
        }
        throw new IllegalArgumentException("there is no product with the given id in the order.");
    }

    //these functions are used to check the validity of the constructor arguments

    private void setDateOfOrder(LocalDateTime dateOfOrder){
        if(dateOfOrder.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("the issuing date of an order cannot be in the future.");
        }
        this.dateOfOrder=dateOfOrder;
    }

    private void setOrderID(int orderID){
        if(orderID<0){
            throw new IllegalArgumentException("an order can't have a negative id.");
        }
        this.orderID=orderID;
    }

    private void setisFixed(Boolean isFixed){
        if(isFixed==null){
            throw new IllegalArgumentException("each order must be fixed or not fixed.");
        }
        this.isFixed=isFixed;
    }

    public LocalDateTime getDateOfOrder() {
        return this.dateOfOrder;
    }

    public ShipmentStatus getShipmentStatus() {
        return this.shipmentStatus;
    }

    public double getPriceBeforeDiscount() {
        return this.priceBeforeDiscount;
    }

    public int getTotalQuantity() {
        return this.totalQuantity;
    }

    public enum ShipmentStatus{
        Delivered,WaitingForDelivery
    }
}