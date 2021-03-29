package BusinessLayer;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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
}