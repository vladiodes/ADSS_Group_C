package main.java.BusinessLayer;

import java.util.Date;
import java.util.Set;

public class Order{
    private Date dateOfOrder;
    private int orderID;
    private boolean shipmentStatus;
    private double priceBeforeDiscount;
    private int totalQuantity;
    private Set<ProductInOrder> productsInOrder;
}