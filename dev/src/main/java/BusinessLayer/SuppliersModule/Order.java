package BusinessLayer.SuppliersModule;

import java.time.LocalDateTime;
import java.util.*;

public class Order{
    private LocalDateTime dateOfOrder;
    private int orderID;
    private ShipmentStatus shipmentStatus;
    private double priceBeforeDiscount;
    private double priceAfterDiscount;
    private int totalQuantity;
    private Set<ProductInOrder> productsInOrder;
    private boolean isFixed;

    public Order(LocalDateTime date,Boolean isFixed,int ID){
        setDateOfOrder(date);
        setOrderID(ID);
        shipmentStatus=ShipmentStatus.WaitingForDelivery;
        priceBeforeDiscount=0;
        priceAfterDiscount=0;
        totalQuantity=0;
        productsInOrder=new LinkedHashSet<>();
        setisFixed(isFixed);
    }

    /**
     *     this constructor is used to reorder fixed orders. it receives an order and an ID and date for the copy
     *     of the order and creates a copy.
     *     throws an exception if the original order isn't fixed (can't be re-ordered)
     * @param original
     * @param ID
     * @param date
     */
    public Order(Order original,int ID,LocalDateTime date){
        if(!original.isFixed)
            throw new IllegalArgumentException("The order you want to re-order from isn't fixed!");
        setDateOfOrder(date);
        setOrderID(ID);
        shipmentStatus=ShipmentStatus.WaitingForDelivery;
        priceBeforeDiscount=original.priceBeforeDiscount;
        this.priceAfterDiscount=original.priceAfterDiscount;
        totalQuantity=original.totalQuantity;
        productsInOrder=new LinkedHashSet<>();
        for(ProductInOrder pio:original.productsInOrder)
            this.productsInOrder.add(pio);
        isFixed=true;
    }

    //simple getters
    public boolean getisFixed(){
        return isFixed;
    }

    public int getOrderID(){
        return orderID;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public double getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Set<ProductInOrder> getProductsInOrder() {
        return productsInOrder;
    }

    /**
     *     this function adds an item to an order. it first searches the productInOrder object matching to that
     *     product.if there is no such object it adds a new entry to the products in order set .otherwise it will
     *     update the already existing object using the orderMore method of productInOrder class.
     * @param productContract
     * @param quantity
     * @param discountsByPrice discounts by price - the map of discounts of the supplier
     */
    public void addItem(Contract productContract, int quantity, Map<Double,Integer> discountsByPrice){
        if(shipmentStatus==ShipmentStatus.Delivered)
            throw new IllegalArgumentException("Can't add items to a delivered order");
        ProductInOrder pio=findProductInOrder(productContract.getProduct());
        if(pio==null){
            pio=new ProductInOrder(quantity,productContract);
            productsInOrder.add(pio);
        }
        else
            pio.orderMore(quantity);
        totalQuantity+=quantity;
        calculateDiscount(discountsByPrice);
    }

    //this functions receives a product and searches for a productInOrder matching to that product.
    //if one is found we return it. otherwise return null.
    private ProductInOrder findProductInOrder(Product product){
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getContract().getProduct().equals(product)){
                return pio;
            }
        }
        return null;
    }


    /**
     *  this function is used when an order is received
     *  simply change the status to received and calculates the final price of the order according to the current
     *  throws an exception if the order was already in the status of delivered
     */
    public void receive() {
        if(shipmentStatus==ShipmentStatus.Delivered)
            throw new IllegalArgumentException("The order was already delivered!");
        shipmentStatus=ShipmentStatus.Delivered;
    }


    /**
     * Removes a product from the order, calculates the price of the order (after and before discount)
     * after the deletion succeeded. If the product doesn't exist in the order, throws an exception
     * @param productContract the contract of the product to delete
     * @param discounts the discounts of the supplier - used to calculate the discount
     */
    public void removeProduct(Contract productContract, Map<Double,Integer> discounts) {
        if (shipmentStatus == ShipmentStatus.Delivered)
            throw new IllegalArgumentException("Can't remove products from already delivered order");
        ProductInOrder pio = findProductInOrder(productContract.getProduct());
        if (pio == null)
            throw new IllegalArgumentException("there is no product with the given id in the order.");
        productsInOrder.remove(pio);
        totalQuantity -= pio.getQuantity();
        calculateDiscount(discounts);
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

    /**
     * Calculates the discount for the order - calculates the highest possible discount
     * @param discounts a map of discounts - [price:discount]
     */
    public void calculateDiscount(Map<Double,Integer> discounts) {
        calculateTotalPriceBeforeDiscount();
        int discount=-1;
        if(discounts==null) {
            priceAfterDiscount = priceBeforeDiscount;
            return;
        }
        for(double price:discounts.keySet()){
            if(priceBeforeDiscount>price)
                discount=Math.max(discount,discounts.get(price));
        }
        if(discount!=-1){
            double discountSum=1-(0.01*discount);
            priceAfterDiscount=priceBeforeDiscount*discountSum;
        }
        else
            priceAfterDiscount=priceBeforeDiscount;
    }

    //calculates the current price before any discounts
    private void calculateTotalPriceBeforeDiscount() {
        priceBeforeDiscount=0;
        for(ProductInOrder pio:productsInOrder){
            pio.calculatePrice();
            priceBeforeDiscount+= pio.getTotalPrice();
        }
    }

    /**
     * Checks if a given products is part of an order that wasn't delivered yet
     * @param p - the product
     * @return true if yes, false otherwise
     */
    public boolean checkIfProductExists(Product p) {
        if(shipmentStatus==ShipmentStatus.Delivered)
            return false;
        return findProductInOrder(p)!=null;
    }

    public enum ShipmentStatus{
        Delivered,WaitingForDelivery;
    }
}