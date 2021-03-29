package BusinessLayer;

import com.sun.deploy.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Supplier{
    private String supplierName;
    private Set<Integer> fixedDays;
    private boolean selfPickUp;
    private int SupplierID;
    private String bankAccount;
    private int paymentMethod;
    private Set<String> categories;
    private Set<String> manufacturers;
    private Map<String,String> contactInfo;
    private Map<Double,Integer> discountsByPrice;
    private List<Order> ordersFromSupplier;
    private List<Contract> supplierContracts;

    public Supplier(int SupplierID,String supplierName, Set<Integer>supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, Set<String> categories, Set<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts){
        this.SupplierID=SupplierID;
        this.supplierName=supplierName;
        setFixedDays(supplyingDays);
        this.selfPickUp=selfPickup;
        this.bankAccount=bankAccount;
        setPaymentMethod(paymentMethod);
        this.categories=categories;
        this.manufacturers=manufactures;
        this.contactInfo=contactInfo;
        this.discountsByPrice=discounts;
        this.ordersFromSupplier=new LinkedList<>();
        this.supplierContracts=new LinkedList<>();
    }

    public String toString(){
        return "supplier name: "+supplierName+'\n'+
                "fixed days: "+fixedDaysToString()+'\n'+
                "self pick up: "+selfPickUp+'\n'+
                "id: "+SupplierID+'\n'+
                "bank account: "+bankAccount+'\n'+
                "categories: "+ categoriesToString()+'\n'+
                "manufacturers: "+manufacturersToString()+'\n'+
                "contact info: "+'\n'+
                contactInfoToString()+'\n'+
                "discounts: "+'\n'+
                discountToString();
                //need to return more?
    }

    private String fixedDaysToString(){
        if(fixedDays==null){
            return "none";
        }
        return StringUtils.join(fixedDays,",");
    }

    private String categoriesToString(){
        return StringUtils.join(categories,",");
    }

    private String manufacturersToString(){
        return StringUtils.join(manufacturers,",");
    }

    private String contactInfoToString(){
        return convertWithStream(contactInfo);
    }

    private String discountToString(){
        return convertWithStream(discountsByPrice);
    }

    private String convertWithStream(Map<?, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", "+'\n', "{", "}"));
        return mapAsString;
    }

    public void setFixedDays(Set<Integer> newFixedDays){
        if(newFixedDays!=null){
            if(newFixedDays.size()==0){
                fixedDays=null;
                return;
            }
            if(newFixedDays.size()>6){
                throw new IllegalArgumentException("illegal fixed days of supplying, can supply on at most 6 days.");
            }
            for (Integer day:
                 newFixedDays) {
                if(!(day>=0 & day<=6)){
                    throw new IllegalArgumentException("illegal fixed days of supplying,can only supply between sunday and friday.");
                }
            }
        }
        fixedDays=newFixedDays;
    }

    public void setPaymentMethod(int method){
        if(method!=0 & method!=1){
            throw new IllegalArgumentException("payment method can only be represented by 0 or 1. 0 for monthly and 1 for per order.");
        }
        paymentMethod=method;
    }

    public void setSelfPickUp(Boolean selfPickUp){
        if(selfPickUp==null){
            throw new IllegalArgumentException("the self pick up status has to false or true so it can't have a null value.")
        }
        this.selfPickUp=selfPickUp;
    }

    public void addDiscount(double price, int discountPerecentage){
        if(price<0 | discountPerecentage<0){
            throw new IllegalArgumentException("tried to add an illegal discount to a supplier. the discount starting price" +
                    "and the discount percentage have to be non negative.");
        }
        discountsByPrice.putIfAbsent(price,discountPerecentage);
    }

    public void addOrder(Date date,Boolean isFixed,int ID){
        ordersFromSupplier.add(new Order(date,isFixed,ID));
    }

    public void reOrder(int newOrderID,int originalOrderID, Date date){
        Order order=findOrder(originalOrderID);
        if(order==null){
            throw new IllegalArgumentException("there is no order with the given ID.");
        }
        if(!order.getisFixed()){
            throw new IllegalArgumentException("the order with the given id isn't fixed so it can't be reordered.");
        }
         ordersFromSupplier.add(new Order(order,newOrderID,date));
    }

    public void addItemToOrder(int orderId, int quantity, int supplierProductId){
        Order order=findOrder(orderId);
        if(order==null){
            throw new IllegalArgumentException("there is no order with the given ID.");
        }
        Contract contract=findContract(supplierProductId);
        if(contract==null){
            throw new IllegalArgumentException("the supplier has no contract for the given product id.");
        }
        order.addItem(contract,quantity);
    }

    private Order findOrder(int id){
        Order order=null;
        for (Order o:
                ordersFromSupplier) {
            if(o.getOrderID()==id){
                order=o;
                break;
            }
        }
        return order;
    }

    private Contract findContract(int supplierProductId){
        Contract contract=null;
        for (Contract c:
             supplierContracts) {
                if(c.getCatalogueIDBySupplier()==supplierProductId){
                    contract=c;
                    break;
                }
        }
        return contract;
    }

}