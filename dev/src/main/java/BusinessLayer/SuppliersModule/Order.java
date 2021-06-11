package BusinessLayer.SuppliersModule;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.Mappers.OrderMapper;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import BusinessLayer.TransportsModule.Controllers.Transports;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Order{

    /**
     * weightOf1Item is for adding this order to a transport, means the weight of 1 item.
     * nothing but a constraint for transports.
     */
    private LocalDate dateOfOrder;
    private int orderID=-1;
    private ShipmentStatus shipmentStatus;
    private double priceBeforeDiscount;
    private double priceAfterDiscount;
    private int totalQuantity;
    private Set<ProductInOrder> productsInOrder;
    private boolean isFixed;
    private int supplierID;

    public Order(LocalDate date,Boolean isFixed,int supplierID){
        setDateOfOrder(date);
        priceBeforeDiscount=0;
        priceAfterDiscount=0;
        totalQuantity=0;
        productsInOrder=new LinkedHashSet<>();
        setisFixed(isFixed);
        this.supplierID=supplierID;
        String siteDestination=SuppliersController.getInstance().getSupplier(supplierID).getSiteDestination();
        if(SuppliersController.getInstance().getSupplier(supplierID).isSelfPickUp()){
            findTransport(siteDestination);
        }
        else{
            shipmentStatus=ShipmentStatus.TransportBySupplier;
        }
    }

    /**
     *     this constructor is used to reorder fixed orders. it receives an order and an ID and date for the copy
     *     of the order and creates a copy.
     *     throws an exception if the original order isn't fixed (can't be re-ordered)
     * @param original
     * @param date
     */
    public Order(Order original,LocalDate date,int supplierID){
        if(!original.isFixed)
            throw new IllegalArgumentException("The order you want to re-order from isn't fixed!");
        setDateOfOrder(date);
        priceBeforeDiscount=original.priceBeforeDiscount;
        this.priceAfterDiscount=original.priceAfterDiscount;
        totalQuantity=original.totalQuantity;
        productsInOrder=new LinkedHashSet<>();
        this.productsInOrder.addAll(original.productsInOrder);
        isFixed=true;
        this.supplierID=supplierID;
        String siteDestination=SuppliersController.getInstance().getSupplier(supplierID).getSiteDestination();
        if(!SuppliersController.getInstance().getSupplier(supplierID).isSelfPickUp()){
            findTransport(siteDestination);
        }
        else{
            shipmentStatus=ShipmentStatus.TransportBySupplier;
        }
    }

    public Order(LocalDate dateOfOrder, int orderID, ShipmentStatus shipmentStatus, double priceBeforeDiscount, double priceAfterDiscount, int totalQuantity, Set<ProductInOrder> productsInOrder, boolean isFixed,int supplierID) {
        this.dateOfOrder=dateOfOrder;
        this.orderID=orderID;
        this.shipmentStatus=shipmentStatus;
        this.priceBeforeDiscount=priceBeforeDiscount;
        this.priceAfterDiscount=priceAfterDiscount;
        this.totalQuantity=totalQuantity;
        this.productsInOrder=productsInOrder;
        this.isFixed=isFixed;
        this.supplierID=supplierID;
    }

//    simple getters
    public int getSupplierID() {return supplierID;}

    public boolean getisFixed(){
        return isFixed;
    }

    public int getOrderID(){
        return orderID;
    }

    public LocalDate getDateOfOrder() {
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
            OrderMapper.getInstance().addItemToOrder(this,pio,productContract.getSupplierID());
        }
        else {
            pio.orderMore(quantity);
            OrderMapper.getInstance().updateItemInOrder(this,pio,pio.getContract().getSupplierID());
        }
        totalQuantity+=quantity;
        calculateDiscount(discountsByPrice);
        OrderMapper.getInstance().update(this,supplierID);

    }

    //this functions receives a product and searches for a productInOrder matching to that product.
    //if one is found we return it. otherwise return null.
    private ProductInOrder findProductInOrder(Item product){
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getContract().getProduct().equals(product)){
                return pio;
            }
        }
        return null;
    }

    /**
     * This function attempts to find an available transport for the given siteDestination - if wasn't found then shipment status
     * is set to no transport available
     * @return true - if found a transport, false otherwise
     */
    public boolean findTransport(String siteDestination) {
        if(siteDestination==null || !(shipmentStatus==ShipmentStatus.NoTransportAvailable || shipmentStatus==null))
            throw new IllegalArgumentException("This order doesn't need a transportation");

        if(!Transports.getInstance().requestTransport(this,siteDestination, SuppliersController.getInstance().getSupplier(supplierID).getFixedDays(),calculateTotalWeight())){
            shipmentStatus=ShipmentStatus.NoTransportAvailable;
            return false;
        }
        else
            shipmentStatus=ShipmentStatus.WaitingForTransport;
        return true;
    }

    /**
     * Calculates the total weight of the order (this param is needed for transport module)
     */
    private int calculateTotalWeight(){
        int output=0;
        for(ProductInOrder pio:productsInOrder)
            output+=pio.getContract().getProduct().getItemWeight()*pio.getQuantity();
        return output;
    }

    /**
     *  this function is used when an order is received
     *  throws an exception if the order was already in the status of delivered
     */
    public void receive() {
        if(!(shipmentStatus==ShipmentStatus.TransportArrived || shipmentStatus==ShipmentStatus.TransportBySupplier)) //those are the only statuses in which the order can be received.
            throw new IllegalArgumentException("This order can't be receive (either transportation hasn't arrived yet\n" +
                    "or no transportation was assigned or the order was already delivered");
        shipmentStatus=ShipmentStatus.Delivered;
        OrderMapper.getInstance().update(this,supplierID);
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
        OrderMapper.getInstance().removeItemFromOrder(this,productContract);
        OrderMapper.getInstance().update(this,supplierID);
    }

    //these functions are used to check the validity of the constructor arguments
    private void setDateOfOrder(LocalDate dateOfOrder){
        if(dateOfOrder.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("The ETA date of the order can't be in the past");
        }
        this.dateOfOrder=dateOfOrder;
    }

    public void setOrderID(int orderID) {
        if (this.orderID == -1)
            this.orderID = orderID;
    }

    private void setisFixed(Boolean isFixed){
        if(isFixed==null){
            throw new IllegalArgumentException("each order must be fixed or not fixed.");
        }
        this.isFixed=isFixed;
        OrderMapper.getInstance().update(this,supplierID);
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
        OrderMapper.getInstance().update(this,supplierID);

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
     * Checks if a given products is part of an order
     * @param p - the product
     * @return true if yes, false otherwise
     */
    public boolean checkIfProductExists(Item p) {
        return findProductInOrder(p)!=null;
    }

    /**
     * This function is called by the transport once it's arrived, updating the field of the shipment status
     * so the storage manager can accept the truck and unpack its contents and place inside the store
     */
    public void transportHasArrived(){
        if(shipmentStatus==ShipmentStatus.WaitingForTransport)
            shipmentStatus=ShipmentStatus.TransportArrived;
        OrderMapper.getInstance().update(this,supplierID);
    }

    public void receiveItem(String pioName, int received, LocalDate expDate) {
        if (!(shipmentStatus == ShipmentStatus.TransportArrived || shipmentStatus == ShipmentStatus.TransportBySupplier)) //those are the only statuses in which the order can be received.
            throw new IllegalArgumentException("This order can't be received (either transportation hasn't arrived yet\n" +
                    "or no transportation was assigned or the order was already delivered");
        ProductInOrder pio = findProductByName(pioName);
        if (pio == null)
            throw new IllegalArgumentException("No such product with this name in the order");
        if(pio.getQuantity()<received)
            throw new IllegalArgumentException("There are less than " + received + " products of " + pioName + "  in the order");
        pio.getContract().getProduct().addSpecificItem(0,received,expDate);
    }

    private ProductInOrder findProductByName(String name){
        for(ProductInOrder pio:productsInOrder){
            if(pio.getContract().getProduct().getName().equals(name))
                return pio;
        }
        return null;
    }

    public enum ShipmentStatus{
        TransportBySupplier,TransportArrived,Delivered,WaitingForTransport,NoTransportAvailable;
    }
}