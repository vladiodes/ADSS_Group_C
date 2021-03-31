package BusinessLayer;

import java.util.*;

public class Order{
    private Date dateOfOrder;
    private int orderID;
    private boolean shipmentStatus;
    private double priceBeforeDiscount;
    private int totalQuantity;
    private Set<ProductInOrder> productsInOrder;
    private boolean isFixed;

    public Order(Date date,Boolean isFixed,int ID){
        dateOfOrder=date;
        orderID=ID;
        shipmentStatus=false;
        priceBeforeDiscount=0;
        totalQuantity=0;
        productsInOrder=new LinkedHashSet<>();
        this.isFixed=isFixed;
    }

    //this constructor is used to reorder fixed orders. it receives a fixed order and an ID and date for the copy
    //of the fixed order and creates a copy.
    public Order(Order original,int ID,Date date){
        dateOfOrder=date;
        orderID=ID;
        shipmentStatus=false;
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
            pio.orderMore(pricePerUnit,quantity);
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

    //a function used to describe an order.
    //it returns a list of 2 strings the first one being the characteristics of the order and the second being
    //a string representing the products in the order.
    public List<String> getOrderDetails(){
        String details="date of order: "+dateOfOrder.toString()+'\n'+
                "id: "+orderID+'\n'+
                "received order: "+shipmentStatus+'\n'+
                "price before discount: "+priceBeforeDiscount+'\n'+
                "is fixed: "+isFixed;

        List<String> order=new LinkedList<>();
        order.add(details);
        for (ProductInOrder pio:
             productsInOrder) {
            order.add(pio.toString());
        }
        return order;
    }

    //this function is used when an order is received.simply change the status to received(true).
    public void receive() {
        shipmentStatus=true;
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
}