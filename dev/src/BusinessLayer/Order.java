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

    public void addItem(Contract contract,int quantity){
        ProductInOrder pio=findProductInOrder(contract);
        if(pio==null){
            productsInOrder.add(new ProductInOrder(quantity,contract.getPricePerUnit()*quantity,contract.getCatalogueIDBySupplier(),this,contract.getProduct()));
        }
        else {
            pio.orderMore(contract,quantity);
        }
    }

    public ProductInOrder findProductInOrder(Contract c){
        Product product=c.getProduct();
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getProduct().equals(product)){
                return pio;
            }
        }
        return null;
    }

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

    public void receive() {
        shipmentStatus=true;
    }

    public void removeProduct(int productID) {
        for (ProductInOrder pio:
             productsInOrder) {
            if(pio.getProduct().getID()==productID)
                productsInOrder.remove(pio);
            return;
        }
        throw new IllegalArgumentException("there is no product with the given id in the order.");
    }
}